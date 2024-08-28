plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.20"
    id("kotlin-parcelize")
    id("de.mannodermaus.android-junit5") version "1.10.0.0"
}

android {
    namespace = "com.cryptoassignment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cryptoassignment"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // Match the Compose Compiler version
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    val room_version = "2.6.1"

    // deps.xxx.dagger2 = androidx.room:room-runtime:$room_version


    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    androidTestImplementation("androidx.room:room-testing:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    androidTestImplementation("app.cash.turbine:turbine:0.9.0")

    // Android test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Unit test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("app.cash.turbine:turbine:0.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    implementation("androidx.compose.ui:ui:1.6.8") // Compose UI
    implementation("androidx.compose.material:material:1.6.8") // Material Design components
    implementation("androidx.compose.foundation:foundation:1.6.8") // Foundation components
    implementation("androidx.compose.runtime:runtime-livedata:1.6.8") // LiveData support
    implementation("androidx.compose.ui:ui-tooling:1.6.8") // Tooling support
    implementation("androidx.activity:activity-compose:1.7.0") // Activity Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
}

kapt {
    correctErrorTypes = true
}