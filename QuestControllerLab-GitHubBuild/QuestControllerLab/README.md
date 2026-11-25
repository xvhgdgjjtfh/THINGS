# xrtcf — Quest Controller Lab (0.1.0)

A small Android settings/profile utility intended to sideload onto Meta Quest 2. It stores three tuning values locally: smoothing, virtual reach, and response. It deliberately does **not** claim to modify Quest system tracking or GILLA.

## Build

1. Install Android Studio and the Android SDK Platform 35.
2. Open this folder in Android Studio and allow Gradle sync.
3. Build > Build Bundle(s) / APK(s) > Build APK(s).
4. The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.
5. Enable Developer Mode and install the APK with Meta Quest Developer Hub or `adb install app-debug.apk`.

## Verification status

- Source project and manifest created.
- Settings persist through Android SharedPreferences.
- Build was **not run** in this environment: Android SDK, Gradle, and ADB are not installed here.
- No APK is included.

## Important technical boundary

Meta's OpenXR runtime exposes controller poses to the OpenXR application that owns the XR session. A normal Android app cannot read or rewrite another game's private OpenXR session. GILLA would need an official integration or a supported mod interface for these values to affect its in-game controller poses. See Meta's OpenXR input documentation: https://developers.meta.com/vr/documentation/native/android/mobile-openxr-input/

## Free cloud build (GitHub Actions)

This project includes `.github/workflows/build-apk.yml` so GitHub can compile it without a paid converter.

1. Sign in to GitHub and create a **new public repository** (public repositories get free Actions usage for standard runners).
2. Extract this ZIP on your PC and upload the *contents* of the `QuestControllerLab` folder to the repository. The `.github/workflows/build-apk.yml` file must be included.
3. Open the repository's **Actions** tab. Select **Build Quest Controller Lab APK** and choose **Run workflow**. (A push to `main` or `master` also starts a build.)
4. When the run finishes successfully, open that run and download the `QuestControllerLab-debug-apk` artifact.
5. Extract the artifact; it contains `app-debug.apk`. Install it on the Quest 2 using Meta Quest Developer Hub or `adb install app-debug.apk`.

The workflow compiles the Android project; it does not add GILLA integration or change another game's private controller tracking session.
