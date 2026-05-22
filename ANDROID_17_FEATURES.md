# Android 17 features and project mapping

This document describes the features showcased in this repository, implementation notes and where to find the code. The examples are intended as small, easy-to-read samples that demonstrate how to use common Android capabilities in a Jetpack Compose app.

Note: The project is named "Android17FeatureShowcase" and aims to demonstrate a handful of features in a concise app. The following sections explain the features implemented in this codebase and provide pointers for testing and extending them.

---

## Features in this project

1. Contacts (Contact picker)
   - Purpose: Demonstrates how to launch the system contact picker and receive a contact Uri.
   - Implementation details:
     - Screen file: `app/src/main/java/com/example/android17featureshowcase/feature/contact/ContactScreen.kt`
     - ViewModel file: `app/src/main/java/com/example/android17featureshowcase/feature/contact/ContactViewModel.kt`
     - Uses Compose helper `rememberLauncherForActivityResult` with `ActivityResultContracts.PickContact()` to open the contacts app and receive the selected contact Uri.
     - Permissions: Picking a contact via the system picker does not necessarily require READ_CONTACTS just to get the Uri; resolving contact details from the Uri may require permissions depending on your usage. Add runtime permission handling if you fetch contact details.

2. Biometric authentication
   - Purpose: Demonstrates how to prompt the user for biometric authentication using the AndroidX Biometric library.
   - Implementation details:
     - Screen file: `app/src/main/java/com/example/android17featureshowcase/feature/biometric/BiometricScreen.kt`
     - Uses `androidx.biometric.BiometricPrompt` to show authentication dialog and handle callbacks.
     - Dependencies: `androidx.biometric:biometric` (and optionally `biometric-ktx`) — these are included via the Gradle version catalog (see `gradle/libs.versions.toml` and `app/build.gradle.kts`).
     - Notes: The sample uses the BiometricPrompt callback to log success. For production use, always handle failure and error callbacks and consider fallback auth methods.

3. Worker (WorkManager sample)
   - Purpose: Shows how to schedule background work using WorkManager and provide a Compose UI that triggers or observes work.
   - Implementation details:
     - Screen file: `app/src/main/java/com/example/android17featureshowcase/feature/worker/WorkerScreen.kt`
     - Dependencies: `androidx.work:work-runtime-ktx` (included in the project catalog).
     - Notes: WorkManager is ideal for deferrable background tasks that must survive app restarts or device reboots. Use constraints to adapt to network, battery, or storage conditions.

4. Adaptive UI sample
   - Purpose: Demonstrates small examples of making UI adapt to available space or configuration.
   - Implementation details:
     - Screen file: `app/src/main/java/com/example/android17featureshowcase/feature/adaptive/AdaptiveScreen.kt`
     - This sample is intentionally small; extend it with Jetpack WindowManager or Compose size classes for multi-window, foldable, or large-screen adaptations.

---

## Runtime and permissions

- Contacts: If you read contact details from the Uri, request `android.permission.READ_CONTACTS` at runtime. The sample app only demonstrates picking a contact Uri via the system contact picker.
- Biometric: No special AndroidManifest permission is needed. Ensure the device or emulator supports biometric authentication and is configured with biometrics.
- Worker: If your worker performs network calls, ensure the app has network permissions if required.

---

## File locations quick map

- Contact UI: `app/src/main/java/com/example/android17featureshowcase/feature/contact/ContactScreen.kt`
- Contact ViewModel: `app/src/main/java/com/example/android17featureshowcase/feature/contact/ContactViewModel.kt`
- Biometric UI: `app/src/main/java/com/example/android17featureshowcase/feature/biometric/BiometricScreen.kt`
- Worker UI: `app/src/main/java/com/example/android17featureshowcase/feature/worker/WorkerScreen.kt`
- Adaptive UI: `app/src/main/java/com/example/android17featureshowcase/feature/adaptive/AdaptiveScreen.kt`
- Reusable UI components: `app/src/main/java/com/example/android17featureshowcase/components/`

---

## Testing the features

1. Contacts:
   - Open the app, go to the Contacts sample, tap "Pick Contact" and select a contact from the system contacts app. The sample will display the selected Uri.

2. Biometric:
   - On a device or emulator with biometric support, open the Biometric sample and tap Authenticate to show the biometric prompt. Use the configured fingerprint/face on the test device or use emulated biometric enrollment on supported emulators.

3. Worker:
   - Trigger the Worker sample from the UI and observe logs/work status. You can inspect WorkManager status using adb or WorkManager testing utilities.

4. Adaptive:
   - Resize the emulator (or use different device profiles) to see adaptive UI changes.

---

## Extending the samples

- Add robust permission handling around contact access and data resolution.
- Expand biometric sample to use cryptographic keys (BiometricPrompt with CryptoObject) for stronger authentication flows.
- Add more WorkManager demonstrations showing periodic work, chained work, and constraint usage.
- Improve adaptive sample by integrating Compose WindowSizeClass or Jetpack WindowManager for foldables and multi-window.

---

If you want, I can also add runtime permission helpers, sample unit tests, or CI Gradle tasks to this repository — tell me which next and I will implement them.

---

## Android 17 — theory, expected platform directions and how they affect these features

Important: Android platform releases evolve rapidly. The section below summarizes the general design directions, platform priorities (privacy, security, power), and concrete migration/recommendation guidance for Contacts, Biometric, WorkManager and Adaptive UI. Where the OS introduces concrete new APIs you should consult the official Android 17 release notes and API reference for exact class and method names — the guidance below is intentionally descriptive and focused on how to adapt code and tests.

High-level platform themes (why things change)
- Privacy-first UX: the platform steadily tightens access to sensitive data (contacts, SMS, location) and encourages using system-provided pickers and one-time or limited-grant permissions.
- Stronger security and authentication: biometric capabilities and passkeys (FIDO) become more integrated with system-level authentication and key management.
- Power & background efficiency: the OS limits background execution; WorkManager and job scheduling APIs evolve to favor predictable, constrained, and observable work.
- Large-screen, foldable and multi-window UX: Android continues to improve adaptive UI primitives (window size classes, foldable regions) and encourages using responsive Compose patterns.

How these themes affect the samples in this repo and practical guidance

1) Contacts (Contact picker and contact data access) — theory and recommendations
- Why it matters: Contacts are sensitive user data. The platform favors system pickers that return URIs with tightly scoped access rather than granting broad long-lived READ_CONTACTS rights.
- What to expect in a modern Android release:
  - Enhanced system contact pickers that return a content Uri with a temporally scoped permission grant (read access for your app while it handles the result).
  - Additional helper APIs or standardized contract improvements to fetch display name, phone and email data from the returned Uri while preserving privacy.
- Practical guidance for this project:
  - Continue to use ActivityResultContracts.PickContact() or the system picker. Treat the returned Uri as the canonical handle and resolve fields only when needed.
  - Prefer requesting a single-use or foreground-only permission when possible. If you must read contact details later, implement a clear user flow and request READ_CONTACTS at runtime with an explanation.
  - Add robust null / permission-denied handling and test flows on devices without contacts or when the user cancels the picker.

2) Biometric (BiometricPrompt, passkeys and authentication theory)
- Why it matters: Biometric authentication is an important platform security primitive; newer releases increasingly integrate platform passkeys and cryptographic key attestation with biometric unlock.
- What to expect in a modern Android release:
  - Improvements to BiometricPrompt UX and reliability across authenticators (fingerprint, face, device credential); tighter defaults for timeouts and fallback handling.
  - Better support for passkeys (FIDO/WebAuthn-style credentials) and platform-bound keys that can be guarded by biometric authentication.
- Practical guidance for this project:
  - Use androidx.biometric.BiometricPrompt and BiometricManager to check availability and handle multiple fallback scenarios (device credential fallback, temporary lockouts).
  - Consider demonstrating a CryptoObject-backed flow: create or load a keystore key, use BiometricPrompt with a CryptoObject so authentication can unlock a private key operation (signing or decryption). This is the recommended pattern for real secure operations (eg. payments, private data access).
  - Handle all callback cases: onAuthenticationSucceeded, onAuthenticationFailed, onAuthenticationError — and provide meaningful UI feedback and fallbacks.

3) Worker / WorkManager (background work theory and migration notes)
- Why it matters: Android aims to reduce surprise background activity to save battery and improve user control. WorkManager evolves to provide clearer guarantees while integrating with platform battery management.
- What to expect in a modern Android release:
  - Slightly stricter scheduling and more emphasis on constrained or foreground work for time-sensitive jobs. The recommended pattern is to use foreground services or foreground work when immediate execution is required and to use WorkManager for deferrable jobs.
  - Improved observability APIs for work progress and completion, and tighter integration with OS-level battery/memory heuristics.
- Practical guidance for this project:
  - Prefer WorkManager for deferrable, persisted tasks; use setForeground() / setForegroundAsync() for tasks that must run immediately while showing a notification.
  - Test work behavior under Doze and battery saver modes. Use WorkManager's test helpers for unit/integration tests and add UI controls in the sample to start, observe, and cancel work.
  - Use constraints (network, storage, charging) to express when work should run, rather than trying to force immediate execution.

4) Adaptive UI (large-screen, foldables, multi-window theory)
- Why it matters: With more diverse device form factors, apps must adapt layout and interactions rather than assume a single-screen portrait layout.
- What to expect in a modern Android release:
  - Higher-level helpers to determine window size classes, folding features and posture, and better compose integration so that adaptive layouts are straightforward.
  - Encouragement to use responsive patterns (single-column -> dual-column) and to treat navigation and side-by-side content explicitly when enough width is available.
- Practical guidance for this project:
  - Adopt Jetpack WindowSizeClass or WindowManager APIs to derive size buckets and device posture. Then create Compose layout branches for Compact/Medium/Expanded width.
  - Test the adaptive sample on multiple emulators and device profiles, including tablets and foldable device configurations (if available in your emulator).
  - Keep state restoration and navigation semantics clear when the layout changes; prefer single-activity Compose patterns and hoisted state so layout recomposition doesn't break UX.

Quick checklists you can add to the sample codebase
- Contacts:
  - [ ] Show friendly rationale before requesting READ_CONTACTS
  - [ ] Use system picker and treat Uri as authoritative
  - [ ] Provide fallback UI when picker is canceled or permission denied

- Biometric:
  - [ ] Use BiometricManager to guard calls
  - [ ] Use CryptoObject-backed flows for real secure operations
  - [ ] Implement full callback handling and fallback UX

- Worker:
  - [ ] Respect constraints and Doze behavior in tests
  - [ ] Use foreground work for immediate user-initiated tasks
  - [ ] Add observable progress reporting to UI

- Adaptive:
  - [ ] Use WindowSizeClass / WindowManager
  - [ ] Provide responsive layout variants for width buckets
  - [ ] Test state and navigation across layout changes

Where to look for the official, up-to-date details
- Android Platform release notes and API reference: https://developer.android.com
- Jetpack libraries (Biometric, WorkManager, WindowManager, Activity/Compose integration) release notes on the AndroidX pages and Maven artifacts.

If you'd like, I can:
- Add concrete code examples to the `feature/contact` and `feature/biometric` samples showing CryptoObject usage and permission rationale UI.
- Add a small WorkManager test harness and a Compose-based observable progress indicator.
- Enhance the adaptive sample to use WindowSizeClass and include screenshots/preview configurations.

Tell me which of the above you'd like implemented and I'll add the code changes and tests.

