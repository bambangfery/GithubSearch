package com.bambang.githubsearch.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["serverId"], unique = true), Index(value = ["userId"])]
)
data class Repo(
    @PrimaryKey (autoGenerate = true) val id: Long = 0,
    val serverId: Int?,
    var name: String?,
    var language: String?,
    var description: String?,
    var userId: Long // Foreign key ke User
)