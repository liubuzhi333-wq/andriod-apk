plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.liufy.thermaldisplay"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.liufy.thermaldisplay"
        minSdk = 23
        targetSdk = 35
        versionCode = 14
        versionName = "V14-Android"
    }
}
