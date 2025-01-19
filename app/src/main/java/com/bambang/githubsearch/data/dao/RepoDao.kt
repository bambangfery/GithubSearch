package com.bambang.githubsearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bambang.githubsearch.data.entity.Repo

@Dao
interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repo: Repo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(repo: Repo): Long

    @Query("SELECT * FROM Repo WHERE userId = :userId")
    fun getReposByUserId(userId: Long): List<Repo>

    @Query("SELECT * FROM Repo WHERE serverId = :id LIMIT 1")
    fun findRepoByPK(id: Int): Repo?

    @Query("DELETE FROM Repo")
    fun deleteAll()
}