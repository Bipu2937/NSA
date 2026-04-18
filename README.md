# Notification Saver

An Android app that captures and stores notifications locally on your device. All data stays on your phone — nothing is uploaded anywhere.

## Download

**[⬇ Download NotificationSaver.apk](NotificationSaver.apk)**

> Direct link: `https://github.com/bipu2937/nsa/raw/claude/add-icon-apk-download-0gZTe/NotificationSaver.apk`

- **Size:** ~12 MB
- **Build type:** debug (unsigned — fine for sideloading)
- **Minimum Android version:** 8.0 (API 26)

## Install

1. Download `NotificationSaver.apk` to your Android device.
2. Enable **Install unknown apps** for your browser or file manager (Settings → Apps → *your app* → Install unknown apps).
3. Tap the APK to install.
4. Open the app and grant **Notification access** when prompted (Settings → Notifications → Notification access).

## Features

- Captures incoming notifications and stores them in a local Room database
- Search saved notifications
- Auto-delete notifications after a configurable number of days
- Optional capture of persistent/ongoing notifications
- Detail view for each saved notification
- Built with Kotlin, Jetpack Compose, and Material 3

## Build from source

```bash
./gradlew assembleDebug
```

The APK will be written to `app/build/outputs/apk/debug/app-debug.apk`.
