# iOS 27 Inspired Launcher

An Android launcher project with an iOS-inspired visual style, designed for Android 8+ and tested through a GitHub Actions APK build workflow.

## Included
- Gradient home screen, date and clock card, widget-style weather/calendar cards.
- App shortcut grid and dock.
- Web search field, theme selector, and shortcuts to Android Wi-Fi, Bluetooth, display, sound, phone, camera, and settings screens.
- A lock-screen **preview activity** with matching themes.
- Native Android security remains in charge: this app does not replace the real lock screen, PIN, password, or biometrics.

## Build APK
1. Open the **Actions** tab in this repository.
2. Open the latest successful **Build Android APK** run.
3. Download the `ios27-inspired-debug-apk` artifact.
4. Extract the ZIP and install `app-debug.apk` on the phone.

Build uses Java 17, Gradle 8.9, Android Gradle Plugin 8.7.3, Kotlin 2.0.21, and Android SDK 35.

## Notes
- This is an original visual concept inspired by iOS-style layouts; it is not affiliated with Apple.
- Weather and calendar cards are decorative widgets. The weather card opens a web search; the calendar card opens an installed calendar app when available.
- A successful CI build confirms compilation, not full device testing. Test shortcuts on your own device.
