
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.infinity.apps.magnisetesttask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.infinity.apps.magnisetesttask"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    // Retrofit2
    implementation(libs.retrofit)
    // Okhttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    // Moshi
    implementation(libs.converter.moshi)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.moshi.kotlin)
    // Navigation
    implementation(libs.androidx.navigation.compose.v274)
    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    // Lottie
    implementation(libs.lottie.compose)
    // Security
    implementation (libs.androidx.security.crypto)
    // Ktor
    implementation (libs.ktor.client.core)
    implementation (libs.ktor.client.cio)
    implementation (libs.ktor.client.websockets)
    // Serialization
    implementation(libs.kotlinx.serialization.json)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(project(":finance"))

    val sciChartVersion = "4.3.0.4686"

    implementation("com.scichart.library:core:$sciChartVersion@aar")
    implementation("com.scichart.library:data:$sciChartVersion@aar")
    implementation("com.scichart.library:drawing:$sciChartVersion@aar")
    implementation("com.scichart.library:charting3d:$sciChartVersion@aar")
    implementation("com.scichart.library:charting:$sciChartVersion@aar")
    implementation("com.scichart.library:extensions:$sciChartVersion@aar")
    implementation("com.scichart.library:extensions3d:$sciChartVersion@aar")


}