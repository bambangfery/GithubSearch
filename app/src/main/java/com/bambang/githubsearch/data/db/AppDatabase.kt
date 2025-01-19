package com.bambang.githubsearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bambang.githubsearch.data.dao.QueriesDao
import com.bambang.githubsearch.data.dao.Query2UserDao
import com.bambang.githubsearch.data.dao.RepoDao
import com.bambang.githubsearch.data.dao.UserDao
import com.bambang.githubsearch.data.entity.Queries
import com.bambang.githubsearch.data.entity.Query2User
import com.bambang.githubsearch.data.entity.Repo
import com.bambang.githubsearch.data.entity.User

@Database(entities = [User::class, Repo::class, Query2User::class, Queries::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
    abstract fun queriesDao(): QueriesDao
    abstract fun query2UserDao(): Query2UserDao
}