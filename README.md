# Android17FeatureShowcase

Small sample Android app that demonstrates a group of feature samples (Contacts, Biometric authentication, WorkManager worker example, and an Adaptive UI sample). The project is laid out as a compact showcase you can build, run and use as a starting point for exploring these features.

---

## Repository structure (high level)

- app/
  - src/main/java/com/example/android17featureshowcase/
    - MainActivity.kt — app entry point
    - MyApp.kt — Compose application wrapper
    - components/ — small reusable Compose components (e.g. FeatureCard)
    - feature/ — screens for each showcased feature
      - contact/ — Contacts picker screen and ViewModel
      - biometric/ — Biometric authentication sample
      - worker/ — WorkManager sample screen
      - adaptive/ — Adaptive layout sample
    - di/ — Dagger/Hilt modules
  - src/main/res/ — resources (themes, drawables, xml)

See the in-repo code for full details of each sample.

---

## Getting started

Prerequisites

- JDK 11
- Android SDK (matching compileSdk in `app/build.gradle.kts` — currently compileSdk = 36)
- Android Studio (recommended) or command-line Gradle

Build and run

From PowerShell (project root):

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug # installs to a connected device/emulator
```

Or open the project in Android Studio and use Run/Debug.

Notes

- The project uses Gradle version catalog (gradle/libs.versions.toml) to manage dependency versions.
- Hilt (Dagger) is used for dependency injection; annotation processing is enabled (kapt).

---

## Implemented feature samples

- Contacts (screen: `feature/contact/ContactScreen.kt`)
  - Uses `ActivityResultContracts.PickContact()` to allow the user to pick a contact from the system contacts app.
  - ViewModel: `feature/contact/ContactViewModel.kt` contains logic for storing the selected contact URI.
  - Required permissions: READ_CONTACTS may be required to fully resolve contact data; the provided sample only receives a contact Uri from the picker.

- Biometric (screen: `feature/biometric/BiometricScreen.kt`)
  - Uses the AndroidX Biometric library (`androidx.biometric:biometric`) and `BiometricPrompt` to show biometric authentication.
  - The project includes the biometric dependency in the Gradle catalog and `app/build.gradle.kts` so the class resolves during compilation.

- Worker (screen: `feature/worker/WorkerScreen.kt`)
  - Demonstrates usage of WorkManager (implementation uses `androidx.work:work-runtime-ktx`).
  - See `feature/worker` for the sample worker and scheduling code.

- Adaptive (screen: `feature/adaptive/AdaptiveScreen.kt`)
  - Shows an example Compose screen that adapts layout/options based on available size or configuration.

---

## Where to look in the code

- `app/src/main/java/com/example/android17featureshowcase/feature/` — feature screens and related code.
- `app/src/main/java/com/example/android17featureshowcase/components/` — small Compose components used by screens.
- `build.gradle.kts` and `gradle/libs.versions.toml` — dependency configuration and versions.

---

## Useful commands

- Clean and build:

```powershell
.\gradlew.bat clean assembleDebug
```

- Run unit tests (if any):

```powershell
.\gradlew.bat test
```

### Build APKs and bundles

- Build a debug APK (clean + assemble):

```powershell
.\gradlew.bat clean assembleDebug
# APK output: app\build\outputs\apk\debug\app-debug.apk
```

- Build a release APK (unsigned if signing not configured):

```powershell
.\gradlew.bat clean assembleRelease
# Unsigned APK: app\build\outputs\apk\release\app-release-unsigned.apk
# If signing is configured, the signed APK will appear as app-release.apk
```

- Build an Android App Bundle (AAB):

```powershell
.\gradlew.bat clean bundleRelease
# AAB output: app\build\outputs\bundle\release\app-release.aab
```

---

## Troubleshooting

- If you see unresolved imports for Hilt, ensure the `androidx.hilt:hilt-navigation-compose` dependency is present and that the Hilt Gradle plugin is applied (this project uses the plugin).
- If `androidx.biometric` classes are unresolved, sync Gradle and ensure `libs.versions.toml` contains the `androidx-biometric` entry (this project already includes it).

---

## Contributing

Feel free to submit PRs to add more feature samples, improve documentation, or modernize examples (e.g. add more robust permission handling, improved adaptive layouts, or unit tests).

---

## License & authors

This sample repository is provided as-is for demonstration and educational purposes. Check project owner for licensing information.

