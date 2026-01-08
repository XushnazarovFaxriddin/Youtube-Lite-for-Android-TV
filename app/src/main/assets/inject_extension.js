// YouTube TV Extension Logic - ported from Chrome extension
// This file is injected after tv_bridge.js on every page load.
(function () {
    'use strict';

    var ytvPlayer = null;
    var initInterval = null;
    var maxAttempts = 120; // 30 seconds max (120 * 250ms)
    var attempts = 0;

    // Find the YouTube player element
    function findPlayer() {
        return document.getElementById('movie_player') || document.querySelector('.html5-video-player');
    }

    // Set playback quality to highest available
    function setHighQuality() {
        if (!ytvPlayer || typeof ytvPlayer.setPlaybackQualityRange !== 'function') {
            return false;
        }

        try {
            var videoData = ytvPlayer.getVideoData();
            if (!videoData || !videoData.video_quality) {
                return false;
            }

            // Set to highest quality (highres = 4K/8K, hd2160 = 4K, hd1440 = 1440p, hd1080 = 1080p)
            ytvPlayer.setPlaybackQualityRange('highres');
            console.log('[YT TV Lite] Set quality to highres');
            return true;
        } catch (e) {
            console.error('[YT TV Lite] Error setting quality:', e);
            return false;
        }
    }

    // Request fullscreen mode
    function requestFullscreen() {
        if (!ytvPlayer) {
            ytvPlayer = findPlayer();
        }

        if (!ytvPlayer) {
            return false;
        }

        try {
            if (typeof ytvPlayer.requestFullscreen === 'function') {
                ytvPlayer.requestFullscreen();
                return true;
            } else if (typeof ytvPlayer.webkitRequestFullscreen === 'function') {
                ytvPlayer.webkitRequestFullscreen();
                return true;
            } else if (typeof ytvPlayer.mozRequestFullScreen === 'function') {
                ytvPlayer.mozRequestFullScreen();
                return true;
            }
        } catch (e) {
            console.error('[YT TV Lite] Error requesting fullscreen:', e);
        }
        return false;
    }

    // Initialize player and set quality
    function initPlayer() {
        ytvPlayer = findPlayer();
        attempts++;

        if (ytvPlayer && typeof ytvPlayer.setPlaybackQualityRange === 'function') {
            if (setHighQuality()) {
                clearInterval(initInterval);
                initInterval = null;
                console.log('[YT TV Lite] Player initialized with HD quality');

                // Also listen for video changes to re-apply HD quality
                setupVideoChangeListener();
            }
        } else if (attempts >= maxAttempts) {
            clearInterval(initInterval);
            initInterval = null;
            console.log('[YT TV Lite] Player initialization timed out');
        }
    }

    // Setup listener for video changes to re-apply HD quality
    function setupVideoChangeListener() {
        if (!ytvPlayer) return;

        try {
            // YouTube TV fires 'onStateChange' when video changes
            if (typeof ytvPlayer.addEventListener === 'function') {
                ytvPlayer.addEventListener('onStateChange', function(state) {
                    // State 1 = playing, -1 = unstarted
                    if (state === 1 || state === -1) {
                        setTimeout(setHighQuality, 500);
                    }
                });
            }
        } catch (e) {
            console.error('[YT TV Lite] Error setting up video change listener:', e);
        }
    }

    // Handle key press events (for fullscreen key code 428)
    function setupKeyHandler() {
        document.body.addEventListener('keydown', function(e) {
            if (!ytvPlayer) {
                ytvPlayer = findPlayer();
            }

            if (!ytvPlayer || typeof ytvPlayer.setPlaybackQualityRange !== 'function') {
                return;
            }

            // Key code 428 triggers fullscreen (from Chrome extension)
            if (e.keyCode === 428) {
                requestFullscreen();
            }
        });
    }

    // Expose functions to global scope for Kotlin bridge
    window.__ytTvExtension = {
        setHighQuality: setHighQuality,
        requestFullscreen: requestFullscreen,
        getPlayer: function() { return ytvPlayer; }
    };

    // Start initialization
    function start() {
        attempts = 0;
        if (initInterval) {
            clearInterval(initInterval);
        }
        initInterval = setInterval(initPlayer, 250);
        setupKeyHandler();
    }

    // Run on DOMContentLoaded or immediately if already loaded
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', start);
    } else {
        start();
    }

    // Also re-initialize on page navigation (for SPA behavior)
    var lastUrl = location.href;
    new MutationObserver(function() {
        if (location.href !== lastUrl) {
            lastUrl = location.href;
            console.log('[YT TV Lite] URL changed, re-initializing...');
            setTimeout(start, 500);
        }
    }).observe(document.body, { subtree: true, childList: true });

    console.log('[YT TV Lite] Extension script loaded');
})();
