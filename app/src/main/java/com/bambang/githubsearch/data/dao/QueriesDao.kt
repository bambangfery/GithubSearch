package com.bambang.githubsearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bambang.githubsearch.data.entity.Queries

@Dao
interface QueriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(query: Queries): Long

    @Query("SELECT * FROM Queries WHERE que = :query LIMIT 1")
    fun findQueryByText(query: String): Queries?

    @Query("SELECT * FROM Queries ORDER BY execDate DESC LIMIT 1")
    fun getLastQueries(): Queries?

    @Query("DELETE FROM Queries")
    fun deleteAll()

    @Update
    fun updateQueries(query: Queries)
}