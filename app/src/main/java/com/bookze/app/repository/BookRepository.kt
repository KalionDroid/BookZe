package com.bookze.app.repository

import com.bookze.app.db.BookDao
import com.bookze.app.model.Book
import javax.inject.Inject

class BookRepository
@Inject constructor(
    private val bookDao: BookDao
) {
    suspend fun insert(book: Book) = bookDao.insertBook(book)

    fun getBooks() = bookDao.getAllBooks()
}