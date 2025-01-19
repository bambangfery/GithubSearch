package com.bambang.githubsearch.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bambang.githubsearch.data.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplace(users: List<User>)

    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    fun findUserById(id: Long): User?

    @Query("SELECT * FROM User WHERE serverId = :serverId LIMIT 1")
    fun findUserByServerId(serverId: Int): User?

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query(
        """
        SELECT u.* FROM User u
        INNER JOIN Query2User q2u ON u.id = q2u.userId
        INNER JOIN Queries q ON q.id = q2u.queryId
        WHERE q.que = :query
        """
    )
    fun findUsersByQuery(query: String): List<User>

    @Transaction
    suspend fun runInTransaction(block: suspend () -> Unit) {
        block()
    }
}