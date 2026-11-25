plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.xrtcf.controllerlab"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.xrtcf.controllerlab"
        minSdk = 29
        targetSdk = 32
        versionCode = 1
        versionName = "0.1.0"
    }
}
