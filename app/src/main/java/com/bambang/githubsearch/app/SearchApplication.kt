package com.bambang.githubsearch.app

import android.app.Application
import com.bambang.githubsearch.api.ApiModule
import com.bambang.githubsearch.data.db.DatabaseModule

class SearchApplication : Application() {

    val appComponent: AppComponent by lazy { buildAppComponent() }

    override fun onCreate() {
        super.onCreate()
        // Konfigurasi aplikasi lainnya
    }

    private fun buildAppComponent(): AppComponent = DaggerAppComponent.builder()
        .databaseModule(DatabaseModule(applicationContext))
        .apiModule(ApiModule())
        .build()

}