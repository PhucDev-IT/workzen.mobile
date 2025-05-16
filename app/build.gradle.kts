plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "vn.gmi.workzen"
    compileSdk = 35

    defaultConfig {
        applicationId = "vn.gmi.workzen"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.karumi:dexter:6.2.3")
    implementation("com.airbnb.android:lottie:6.5.2")

    //Network
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // CameraX
    implementation(libs.camera2)
    implementation(libs.camera.core)
    implementation(libs.camera.view)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.extensions)

    // MLKIT
    implementation("com.google.android.gms:play-services-mlkit-barcode-scanning:18.3.1")
    implementation("com.google.guava:guava:33.3.0-android")
    implementation ("androidx.camera:camera-mlkit-vision:1.3.0-beta02")
    implementation("com.google.android.gms:play-services-mlkit-text-recognition:19.0.1")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
}