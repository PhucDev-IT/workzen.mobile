plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("io.realm.kotlin")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
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
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }


    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    flavorDimensions.add("dev")
    productFlavors {
        create("dev") {
                buildConfigField("String", "API_BASE_URL", "\"http://localhost:8080/\"")
                buildConfigField("String", "WEB_SOCKET_URL", "\"ws://192.168.201.9:8080/ws\"")
        }
        create("production") {

        }
    }
}
//adb reverse tcp:8080 tcp:8080
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.karumi:dexter:6.2.3")
    implementation("com.airbnb.android:lottie:6.5.2")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.github.stfalcon-studio:StfalconImageViewer:1.0.1")
    implementation("io.realm.kotlin:library-base:2.2.0")

    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    implementation("androidx.work:work-runtime-ktx:2.10.1")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    implementation("androidx.media3:media3-exoplayer:1.7.1")
    implementation("androidx.media3:media3-ui:1.7.1")
    //Network
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.github.NaikSoftware:StompProtocolAndroid:1.6.6")

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


    implementation("com.github.alexzhirkevich:custom-qr-generator:2.0.0-alpha01")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))

}