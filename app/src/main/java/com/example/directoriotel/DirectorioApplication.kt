package com.example.directoriotel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.google.android.libraries.places.api.Places

@HiltAndroidApp
class DirectorioApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, "AIzaSyDXf9lvT_JywrQevztg3O85O8X6gqdI0lA")
    }
}