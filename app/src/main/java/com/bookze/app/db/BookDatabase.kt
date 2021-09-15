package com.bookze.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bookze.app.model.Book

@Database(entities = [Book::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun getBookDao():BookDao
}