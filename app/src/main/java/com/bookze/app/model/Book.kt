package com.bookze.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookTable")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookName: String,
    val authorName: String,
    val bookDescription: String,
    val created: Long = System.currentTimeMillis()
)
