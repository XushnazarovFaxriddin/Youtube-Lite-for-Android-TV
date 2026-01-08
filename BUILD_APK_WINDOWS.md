# Build APK on Windows (Android Studio or CLI)

## Option A: Android Studio (recommended)
1. Install Android Studio.
2. Open this project folder (the one containing `settings.gradle.kts`).
3. Let it sync (it may download Gradle + Android components).
4. `Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`.
5. APK output: `app\\build\\outputs\\apk\\debug\\app-debug.apk`

## Option B: Command line (PowerShell)
Pre-req: Android SDK installed (Android Studio provides it).

```powershell
cd .\YouTubeTVLiteBuild
.\gradlew.bat :app:assembleDebug
```

APK output:
- `app\build\outputs\apk\debug\app-debug.apk`
