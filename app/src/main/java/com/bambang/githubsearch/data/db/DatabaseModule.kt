package com.bambang.githubsearch.data.db

import android.content.Context
import androidx.room.Room
import com.bambang.githubsearch.data.UserLocalModel
import com.bambang.githubsearch.data.dao.QueriesDao
import com.bambang.githubsearch.data.dao.Query2UserDao
import com.bambang.githubsearch.data.dao.RepoDao
import com.bambang.githubsearch.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "github_search_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideRepoDao(database: AppDatabase): RepoDao {
        return database.repoDao()
    }

    @Provides
    @Singleton
    fun provideQueriesDao(database: AppDatabase): QueriesDao {
        return database.queriesDao()
    }

    @Provides
    @Singleton
    fun provideQuery2UserDao(database: AppDatabase): Query2UserDao {
        return database.query2UserDao()
    }

}