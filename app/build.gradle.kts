plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    kotlin ("kapt") //Correccion de errores
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.directoriotel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.directoriotel"
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

    // Room
    val room_version = "2.6.1"
    implementation ("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // Dagger Core
    implementation ("com.google.dagger:dagger:2.46.1")
    kapt ("com.google.dagger:dagger-compiler:2.46.1")

    // Dagger Android
    api ("com.google.dagger:dagger-android:2.46.1")
    api ("com.google.dagger:dagger-android-support:2.46.1")
    kapt ("com.google.dagger:dagger-android-processor:2.46.1")

    //  Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.46.1")
    kapt ("com.google.dagger:hilt-compiler:2.46.1")

    // Swipe
    implementation ("me.saket.swipe:swipe:1.1.1")

    //--Lottie
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    //-- Lottie
    implementation ("com.airbnb.android:lottie-compose:5.2.0")

    //--Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //--DataStore
    implementation ("androidx.datastore:datastore-preferences:1.1.7")

    // Para manejo de imágenes
    implementation ("io.coil-kt:coil-compose:2.4.0")
    implementation ("io.coil-kt:coil:2.4.0")

    // Para permisos en Android 13+
    implementation ("com.google.accompanist:accompanist-permissions:0.30.1")

    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.libraries.places:places:3.4.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}