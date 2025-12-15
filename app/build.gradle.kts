plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.zeeumsiraji_10_locationjetpackjob_3"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.zeeumsiraji_10_locationjetpackjob_3"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX Core & Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Firebase

    // Google / Play Services
    // Credentials API
                                 // Google Sign-In
    implementation("com.google.android.gms:play-services-maps:19.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.gms:play-services-base:18.9.0")
    // Maps Compose for Jetpack Compose
    implementation("com.google.maps.android:maps-compose:2.14.0")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.firebase.firestore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // Debug tooling
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
//navigation
    implementation("androidx.navigation:navigation-compose:2.9.6")

    implementation("androidx.compose.material3:material3:1.2.0")

   // ktor-client
    implementation("io.ktor:ktor-client-cio:2.3.5")
// replace with your Ktor version
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-gson:2.3.5")
    // Coil for Compose
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Room
    ksp("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")

    implementation("androidx.compose.runtime:runtime:1.10.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.10.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.10.0")
}
