plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.armeasure"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.armeasure"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.core:core-ktx:1.13.1")
    // ARCore를 쉽게 쓰게 해주는 SceneView 라이브러리 (ARCore 포함)
    implementation("io.github.sceneview:arsceneview:2.2.1")
}
