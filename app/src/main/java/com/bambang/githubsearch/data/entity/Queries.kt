package com.bambang.githubsearch.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(indices = [Index(value = ["que"], unique = true)])
data class Queries(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val que: String?,
    var execDate: Long?
)