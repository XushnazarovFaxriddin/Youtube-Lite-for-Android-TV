package com.example.youtubetvlite

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progress: ProgressBar
    private lateinit var menuOverlay: LinearLayout

    private var lastBackPressMs: Long = 0
    private var backPressCount: Int = 0
    private var remoteAssistEnabled: Boolean = false

    private val prefs by lazy { getSharedPreferences("yt_tv_lite", MODE_PRIVATE) }

    // Handler for delayed back press reset
    private val backPressHandler = android.os.Handler(android.os.Looper.getMainLooper())

    // TV User-Agent (Sony BRAVIA 8K) - mimics a real TV device for YouTube TV interface
    private val tvUserAgent: String =
        "Mozilla/5.0 (Linux; Andr0id 9; BRAVIA 8K UR2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36 OPR/46.0.2207.0 OMI/4.21.0.273.DIA6.149 Model/Sony-BRAVIA-8K-UR2"

    // Desktop User-Agent as fallback
    private val desktopUserAgent: String =
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progress = findViewById(R.id.progress)
        menuOverlay = findViewById(R.id.menuOverlay)

        remoteAssistEnabled = prefs.getBoolean("remote_assist", false)

        setupMenu()
        setupWebView()

        if (savedInstanceState == null) {
            loadHome()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        webView.requestFocus()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressHandler.removeCallbacksAndMessages(null)
    }

    private fun hideSystemUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    private fun setupMenu() {
        val btnHome: Button = findViewById(R.id.btnHome)
        val btnBack: Button = findViewById(R.id.btnBack)
        val btnForward: Button = findViewById(R.id.btnForward)
        val btnReload: Button = findViewById(R.id.btnReload)
        val btnClearCookies: Button = findViewById(R.id.btnClearCookies)
        val btnToggleUA: Button = findViewById(R.id.btnToggleUA)
        val btnRemoteAssist: Button = findViewById(R.id.btnRemoteAssist)
        val btnCloseMenu: Button = findViewById(R.id.btnCloseMenu)

        fun updateRemoteAssistLabel() {
            val onOff = if (remoteAssistEnabled) "ON" else "OFF"
            btnRemoteAssist.text = getString(R.string.menu_remote_assist, onOff)
        }
        updateRemoteAssistLabel()

        btnHome.setOnClickListener {
            hideMenu()
            loadHome()
        }

        btnBack.setOnClickListener {
            hideMenu()
            goBack()
        }

        btnForward.setOnClickListener {
            hideMenu()
            if (webView.canGoForward()) webView.goForward()
        }

        btnReload.setOnClickListener {
            hideMenu()
            webView.reload()
        }

        btnClearCookies.setOnClickListener {
            hideMenu()
            clearCookies()
        }

        btnToggleUA.setOnClickListener {
            hideMenu()
            toggleUserAgentAndReload()
        }

        btnRemoteAssist.setOnClickListener {
            remoteAssistEnabled = !remoteAssistEnabled
            prefs.edit().putBoolean("remote_assist", remoteAssistEnabled).apply()
            updateRemoteAssistLabel()
            Toast.makeText(this, "Remote assist: " + (if (remoteAssistEnabled) "ON" else "OFF"), Toast.LENGTH_SHORT).show()
        }

        btnCloseMenu.setOnClickListener {
            hideMenu()
            webView.requestFocus()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        val s: WebSettings = webView.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.databaseEnabled = true
        s.loadsImagesAutomatically = true
        s.javaScriptCanOpenWindowsAutomatically = true
        s.mediaPlaybackRequiresUserGesture = false
        s.loadWithOverviewMode = true
        s.useWideViewPort = true
        s.builtInZoomControls = false
        s.displayZoomControls = false
        s.setSupportZoom(false)

        // Default to TV User-Agent for best YouTube TV experience
        val useTvUa = prefs.getBoolean("use_tv_ua", true)
        s.userAgentString = if (useTvUa) tvUserAgent else desktopUserAgent

        webView.isFocusable = true
        webView.isFocusableInTouchMode = true

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progress.visibility = if (newProgress in 1..95) View.VISIBLE else View.GONE
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                injectAssets(view)
            }
        }
    }

    private fun loadHome() {
        webView.loadUrl("https://www.youtube.com/tv")
    }

    /**
     * Handle back navigation for YouTube TV.
     * YouTube TV is a Single Page Application (SPA) - it handles its own navigation via Escape key.
     * We should NOT use webView.goBack() as it breaks YouTube's internal navigation.
     * Instead, we send Escape to YouTube and use triple-back-press to exit the app.
     */
    private fun handleBack() {
        val now = SystemClock.elapsedRealtime()

        // Reset back press count if more than 2 seconds since last press
        if (now - lastBackPressMs > 2000) {
            backPressCount = 0
        }

        backPressCount++
        lastBackPressMs = now

        // Always send Escape to YouTube TV first - it handles back navigation internally
        sendKeyToWeb("Escape")

        // Check current URL to determine exit behavior
        val currentUrl = webView.url ?: ""
        val isOnHomePage = currentUrl == "https://www.youtube.com/tv" ||
                           currentUrl == "https://www.youtube.com/tv#/" ||
                           currentUrl.matches(Regex("https://www\\.youtube\\.com/tv/?#?/?$"))

        if (isOnHomePage) {
            // On home page - double back to exit
            if (backPressCount >= 2) {
                finish()
            } else {
                Toast.makeText(this, "Chiqish uchun BACK ni yana bosing", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Inside YouTube (video, search, etc.) - triple back to exit
            // First two presses let YouTube handle navigation, third press exits
            if (backPressCount >= 3) {
                Toast.makeText(this, "Chiqish uchun BACK ni yana bosing", Toast.LENGTH_SHORT).show()
                backPressCount = 0 // Reset to require confirmation
            }
        }

        // Reset count after delay
        backPressHandler.removeCallbacksAndMessages(null)
        backPressHandler.postDelayed({ backPressCount = 0 }, 2500)
    }

    /**
     * Legacy goBack for menu button - uses WebView history
     */
    private fun goBack() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            handleBack()
        }
    }

    private fun clearCookies() {
        val cm = CookieManager.getInstance()
        cm.removeAllCookies(null)
        cm.flush()
        Toast.makeText(this, "Cookies cleared", Toast.LENGTH_SHORT).show()
        webView.reload()
    }

    private fun toggleUserAgentAndReload() {
        val current = prefs.getBoolean("use_tv_ua", true)
        prefs.edit().putBoolean("use_tv_ua", !current).apply()

        webView.settings.userAgentString = if (!current) tvUserAgent else desktopUserAgent
        Toast.makeText(
            this,
            "User-Agent: " + (if (!current) "TV (Sony BRAVIA)" else "Desktop"),
            Toast.LENGTH_SHORT
        ).show()
        webView.reload()
    }

    private fun showMenu() {
        if (menuOverlay.visibility != View.VISIBLE) {
            menuOverlay.visibility = View.VISIBLE
            menuOverlay.getChildAt(0)?.requestFocus()
        }
    }

    private fun hideMenu() {
        if (menuOverlay.visibility == View.VISIBLE) {
            menuOverlay.visibility = View.GONE
        }
    }

    private fun toggleMenu() {
        if (menuOverlay.visibility == View.VISIBLE) hideMenu() else showMenu()
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                showMenu()
                true
            }
            else -> super.onKeyLongPress(keyCode, event)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // Menu open: BACK closes menu.
        if (menuOverlay.visibility == View.VISIBLE) {
            if (event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_BACK) {
                hideMenu()
                webView.requestFocus()
                return true
            }
            return super.dispatchKeyEvent(event)
        }

        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_MENU,
                KeyEvent.KEYCODE_SETTINGS,
                KeyEvent.KEYCODE_INFO -> {
                    toggleMenu()
                    return true
                }

                KeyEvent.KEYCODE_BACK -> {
                    // Let YouTube TV handle back navigation via Escape key
                    // YouTube TV is a SPA and manages its own navigation
                    handleBack()
                    return true
                }

                // Remote assist mode: translate DPAD to JS keyboard events.
                KeyEvent.KEYCODE_DPAD_UP -> {
                    if (remoteAssistEnabled) {
                        sendKeyToWeb("ArrowUp")
                        return true
                    }
                }

                KeyEvent.KEYCODE_DPAD_DOWN -> {
                    if (remoteAssistEnabled) {
                        sendKeyToWeb("ArrowDown")
                        return true
                    }
                }

                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    if (remoteAssistEnabled) {
                        sendKeyToWeb("ArrowLeft")
                        return true
                    }
                }

                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    if (remoteAssistEnabled) {
                        sendKeyToWeb("ArrowRight")
                        return true
                    }
                }

                KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                    if (remoteAssistEnabled) {
                        sendKeyToWeb("Enter")
                        evalJs("window.__tvBridge && window.__tvBridge.click && window.__tvBridge.click();")
                        return true
                    }
                }
            }
        }

        return super.dispatchKeyEvent(event)
    }

    private fun sendKeyToWeb(key: String) {
        // Dispatch a keyboard event inside the page (best-effort).
        val safeKey = key.replace("'", "\\'")
        val js = "window.__tvBridge && window.__tvBridge.key && window.__tvBridge.key('${safeKey}');"
        webView.evaluateJavascript(js, null)
    }

    private fun injectAssets(view: WebView) {
        val bridge = readAsset("tv_bridge.js")
        val ext = readAsset("inject_extension.js")

        view.evaluateJavascript(bridge, null)
        view.evaluateJavascript(ext, null)
    }

    private fun evalJs(js: String) {
        webView.evaluateJavascript(js, null)
    }

    private fun readAsset(fileName: String): String {
        return assets.open(fileName).bufferedReader(Charsets.UTF_8).use { it.readText() }
    }
}
