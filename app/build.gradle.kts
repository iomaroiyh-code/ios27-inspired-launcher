plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.rexona.ios27launcher"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.rexona.ios27launcher"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
