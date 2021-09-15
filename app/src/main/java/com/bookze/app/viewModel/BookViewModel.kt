package com.bookze.app.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookze.app.model.Book
import com.bookze.app.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    val bookData: MutableState<List<Book>> = mutableStateOf(listOf())

    init {
        getAllBooks()
    }


    fun insertBook(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }

    private fun getAllBooks() = viewModelScope.launch {
        repository.getBooks()
            .catch { e ->
                Log.e(TAG, "getAllBooks: ", e)
            }.collect { bookLists ->
                bookData.value = bookLists
            }
    }


    companion object {
        private const val TAG = "BookViewModel"
    }
}

