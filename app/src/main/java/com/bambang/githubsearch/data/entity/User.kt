package com.bambang.githubsearch.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(value = ["serverId"], unique = true)])
data class User(
    @PrimaryKey (autoGenerate = true) val id: Long = 0,
    var login: String?,
    val serverId: Int?,
    var avatarUrl: String?,
    var name: String?,
    var bio: String?
)