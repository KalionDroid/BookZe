package com.bookze.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bookze.app.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(book: Book)

    @Query("SELECT * FROM bookTable ORDER BY created DESC")
    fun getAllBooks(): Flow<List<Book>>
}