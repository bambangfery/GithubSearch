package com.bambang.githubsearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bambang.githubsearch.data.entity.Query2User
import com.bambang.githubsearch.data.entity.User

@Dao
interface Query2UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(query2User: Query2User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(query2User: Query2User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(query2Users: List<Query2User>)

    @Query("DELETE FROM Query2User WHERE queryId = :queryId")
    fun deleteByQueryId(queryId: Long)

    @Transaction
    @Query("""
        SELECT User.* FROM User 
        INNER JOIN Query2User ON User.id = Query2User.userId 
        WHERE Query2User.queryId = :queryId
    """)
    fun getUsersForQuery(queryId: Long): List<User>

    @Query("DELETE FROM Query2User")
    fun deleteAll()
}
