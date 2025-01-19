package com.bambang.githubsearch.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bambang.githubsearch.data.UserLocalModel
import com.bambang.githubsearch.data.dao.QueriesDao
import com.bambang.githubsearch.data.dao.Query2UserDao
import com.bambang.githubsearch.data.dao.RepoDao
import com.bambang.githubsearch.data.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "github_search_database"
        )
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.execSQL("PRAGMA foreign_keys=ON;")  // Aktifkan foreign key
                }
            })
            .build()
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