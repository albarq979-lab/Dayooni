# Dayooni

Dayooni is a Kotlin + Jetpack Compose Android debt manager.

## Stack
- Android 7.0+ (API 24)
- Target/compile SDK 34
- Kotlin 2.0.21
- Jetpack Compose Material 3
- Room + Hilt + WorkManager + DataStore
- Biometric authentication
- Gson JSON backup/restore
- MPAndroidChart
- PDF export using Android PdfDocument

## Build
```bash
./gradlew assembleDebug
```

The GitHub Actions workflow builds the debug APK and uploads it as an artifact.

## Gradle wrapper JAR
The `gradle-wrapper.jar` is generated/provided by the Gradle Wrapper. If you clone this repository without it, install Gradle 8.7 locally and run:

```bash
gradle wrapper --gradle-version 8.7
```

This creates `gradle/wrapper/gradle-wrapper.jar` and the wrapper scripts. The repository also includes the wrapper properties pinned to Gradle 8.7.
