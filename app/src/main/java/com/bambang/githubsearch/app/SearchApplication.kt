package com.bambang.githubsearch.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Konfigurasi aplikasi lainnya
    }
}