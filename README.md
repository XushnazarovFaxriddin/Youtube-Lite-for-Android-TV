# YouTube Lite for Android TV

<div align="center">

![Platform](https://img.shields.io/badge/Platform-Android%20TV-green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-21%20(Android%205.0)-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)
[![Release](https://img.shields.io/github/v/release/XushnazarovFaxriddin/Youtube-Lite-for-Android-TV?include_prereleases)](../../releases/latest)
[![Downloads](https://img.shields.io/github/downloads/XushnazarovFaxriddin/Youtube-Lite-for-Android-TV/total)](../../releases)

**The best solution for Android TV Box devices where YouTube is no longer supported!**

[<img src="https://img.shields.io/badge/Download-APK-red?style=for-the-badge&logo=android" alt="Download APK" height="40">](../../releases/latest)

</div>

---

## Why This App?

Google has discontinued YouTube app support on older Android TV Box devices. If you're affected, you have two options:

| Option | Description |
|--------|-------------|
| **YouTube Lite** (this app) | Lightweight, fast, resource-efficient WebView app. Ideal for older and low-spec devices. |
| **[SmartTube](https://github.com/yuliskov/SmartTube)** | Full-featured app with no ads. Recommended for powerful devices. |

> **Recommendation:** Try YouTube Lite first. If your device runs slow or has issues, try [SmartTube](https://github.com/yuliskov/SmartTube) instead.

---

## Download

### Latest Release

[<img src="https://img.shields.io/badge/Download%20Latest%20APK-green?style=for-the-badge&logo=github" alt="Download" height="50">](../../releases/latest)

Or go directly to [**Releases Page**](../../releases) to see all versions.

### Direct Link

| File | Description |
|------|-------------|
| [**YouTube-Lite-TV.apk**](../../releases/latest) | Latest stable release - works on all devices |

---

## Features

### Core Features

- **YouTube TV Interface** - Optimized for remote control navigation
- **High Quality Video** - Automatic quality up to 4K/8K
- **Lightweight & Fast** - WebView-based, minimal resource usage
- **Fullscreen Mode** - Immersive fullscreen support
- **Google Account Login** - Sign in with your Google account

### Remote Control

| Button | Function |
|--------|----------|
| **D-Pad (Arrows)** | Navigation |
| **OK/Enter** | Select |
| **Back** | Go back / Exit video |
| **Menu/Settings** | Open menu |
| **OK (long press)** | Open menu |

### Menu Functions

- **Home** - Return to home page
- **Back/Forward** - Navigation history
- **Reload** - Refresh page
- **Clear Cookies** - Clear cookies (for re-login)
- **Toggle UA** - Switch User-Agent (TV/Desktop)
- **Remote Assist** - Send D-Pad as JavaScript events

---

## Installation

### Method 1: Direct APK Install

1. [Download the APK](../../releases/latest)
2. Transfer to your TV Box via USB
3. Open with a file manager
4. Allow "Install from unknown sources"
5. Install and launch

### Method 2: ADB Install

```bash
# With ADB connected
adb install YouTube-Lite-TV.apk
```

### Method 3: Downloader App

1. Install **Downloader** app on your TV Box
2. Enter the APK URL from releases page
3. Download and install

---

## Requirements

| Requirement | Minimum | Recommended |
|-------------|---------|-------------|
| Android Version | 5.0 (API 21) | 7.0+ |
| RAM | 1 GB | 2 GB+ |
| Internet | Required | Stable connection |

**Supported Devices:**
- Android TV Box (all brands)
- Android TV (Sony, Philips, TCL, etc.)
- Fire TV Stick (via sideload)
- Mi Box / Mi TV Stick
- NVIDIA Shield
- Other Android TV devices

---

## Troubleshooting

### YouTube Not Loading

1. **Check internet** - Verify Wi-Fi or Ethernet connection
2. **Clear cache** - Settings > Apps > YouTube Lite > Clear Cache
3. **Clear cookies** - Use "Clear Cookies" from app menu

### Remote Not Working

1. Enable **Remote Assist** mode from menu
2. Restart the app
3. Check remote batteries

### Slow Video Loading

1. Check internet speed
2. Switch to Desktop mode via **Toggle UA**
3. Restart your device

### Can't Login

1. Press "Clear Cookies"
2. Try logging in again
3. If 2FA enabled, use TV code method

---

## Alternative: SmartTube

If YouTube Lite doesn't work well on your device or you need more features, we recommend **SmartTube**:

**SmartTube Features:**
- Ad-free YouTube
- SponsorBlock integration
- 4K/8K HDR support
- Picture-in-Picture mode
- Playback speed control
- And many more features

**Download:** [github.com/yuliskov/SmartTube](https://github.com/yuliskov/SmartTube)

> **Note:** SmartTube requires more resources. YouTube Lite may work better on low-spec devices.

---

## FAQ

<details>
<summary><b>Is this app safe?</b></summary>

Yes. The app is open source and only opens YouTube.com/tv. No data is collected or sent anywhere.
</details>

<details>
<summary><b>Is my Google account safe?</b></summary>

Yes. Login happens directly through YouTube.com. Your password is only sent to Google servers.
</details>

<details>
<summary><b>Why don't some features work?</b></summary>

The YouTube TV interface has limited functionality. For full features, try SmartTube.
</details>

<details>
<summary><b>How do I update the app?</b></summary>

Download the new version from the [Releases page](../../releases) and reinstall.
</details>

<details>
<summary><b>Back button not working properly?</b></summary>

The app sends Escape key to YouTube TV for navigation. If you're on the home page, press Back twice to exit. Inside videos, just press Back once to exit the player.
</details>

---

## For Developers

### Building from Source

```bash
# Clone repository
git clone https://github.com/XushnazarovFaxriddin/Youtube-Lite-for-Android-TV.git
cd Youtube-Lite-for-Android-TV

# Build debug APK
./gradlew assembleDebug

# APK location
# app/build/outputs/apk/debug/app-debug.apk
```

### Project Structure

```
app/src/main/
├── java/com/example/youtubetvlite/
│   └── MainActivity.kt          # Main Activity
├── assets/
│   ├── tv_bridge.js             # Android-JS bridge
│   └── inject_extension.js      # YouTube optimization
├── res/
│   ├── layout/activity_main.xml # UI layout
│   ├── drawable/                # Icons
│   └── values/                  # Strings, styles
└── AndroidManifest.xml          # App configuration
```

### CI/CD

Every push to main branch automatically:
1. Builds the APK
2. Creates/updates a GitHub Release
3. Attaches the APK for download

---

## License

MIT License - Free to use, modify, and distribute.

---

## Contributing

- **Issues & Suggestions:** [GitHub Issues](../../issues)
- **Pull Requests:** Contributions welcome!

---

<div align="center">

**If you find this project useful, please give it a star!**

[![Star](https://img.shields.io/github/stars/XushnazarovFaxriddin/Youtube-Lite-for-Android-TV?style=social)](../../stargazers)

Made with ❤️ for Android TV users

</div>
