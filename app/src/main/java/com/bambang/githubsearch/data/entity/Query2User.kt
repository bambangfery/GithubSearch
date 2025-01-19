package com.bambang.githubsearch.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["queryId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Queries::class,
            parentColumns = ["id"],
            childColumns = ["queryId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["queryId"]), Index(value = ["userId"])]
)
data class Query2User(
    val queryId: Long, // Foreign key ke Queries
    val userId: Long   // Foreign key ke User
)