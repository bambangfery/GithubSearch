package com.bambang.githubsearch.app

import com.bambang.githubsearch.api.ApiModule
import com.bambang.githubsearch.data.UserLocalModel
import com.bambang.githubsearch.data.UserRemoteModel
import com.bambang.githubsearch.data.db.DatabaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * Provide Application scope dependencies
 */
@Singleton
@Component(modules = arrayOf(DatabaseModule::class, ApiModule::class))
interface AppComponent {

    fun userLocalModel(): UserLocalModel

    fun userRemoteModel(): UserRemoteModel

}