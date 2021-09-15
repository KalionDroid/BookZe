package com.bookze.app

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.bookze.app.model.Book
import com.bookze.app.ui.theme.BookZeTheme
import com.bookze.app.ui.theme.Teal201
import com.bookze.app.viewModel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookActivity : ComponentActivity() {

    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookZeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Toolbar()
                }
            }
        }
    }

    @Composable
    fun Toolbar() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "BookZe")
                    }
                )
            },
            floatingActionButton = {
                val openDialog = remember { mutableStateOf(false) }
                FloatingActionButton(onClick = {
                    openDialog.value = true
                }, backgroundColor = Teal201) {
                    AddBookDialogBox(openDialog = openDialog)
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {
            Recyclerview(bookViewModel)
        }
    }


    @Composable
    fun AddBookDialogBox(openDialog: MutableState<Boolean>) {
        val bookTitle = remember { mutableStateOf("") }
        val bookAuthor = remember { mutableStateOf("") }
        val bookDescription = remember { mutableStateOf("") }

        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Book")
                },
                text = {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = bookTitle.value,
                            onValueChange = {
                                bookTitle.value = it
                            },
                            label = {
                                Text(text = "Title")
                            }, modifier = Modifier.padding(top = 10.dp)
                        )
                        OutlinedTextField(
                            value = bookAuthor.value,
                            onValueChange = {
                                bookAuthor.value = it
                            },
                            label = {
                                Text(text = "Author")
                            }, modifier = Modifier.padding(top = 10.dp)
                        )
                        OutlinedTextField(
                            value = bookDescription.value,
                            onValueChange = {
                                bookDescription.value = it
                            },
                            label = {
                                Text(text = "Description")
                            }, modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                },
                confirmButton = {
                    OutlinedButton(onClick = {
                        insertBookData(bookTitle, bookAuthor, bookDescription)
                        openDialog.value = false
                    }) {
                        Text(text = "Submit")
                    }
                }
            )
        }
    }

    private fun insertBookData(
        bookName: MutableState<String>,
        authorName: MutableState<String>,
        bookDescription: MutableState<String>
    ) {
        lifecycleScope.launchWhenStarted {
            if (!TextUtils.isEmpty(bookName.value) && !TextUtils.isEmpty(authorName.value) && !TextUtils.isEmpty(
                    bookDescription.value
                )
            ) {
                bookViewModel.insertBook(
                    Book(
                        bookName = bookName.value,
                        authorName = authorName.value,
                        bookDescription = bookDescription.value
                    )
                )
                Toast.makeText(this@BookActivity, "Book Inserted Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    this@BookActivity,
                    "Please fill all the fields to add book",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @Composable
    fun Recyclerview(bookViewModel: BookViewModel) {
        LazyColumn {
            items(bookViewModel.bookData.value) { book ->
                BookListItem(book = book)
            }
        }
    }

    @Composable
    fun BookListItem(book: Book) {

        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = book.bookName, style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        BookImage()
                    }
                    Text(text = book.authorName, style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(text = book.bookDescription, style = MaterialTheme.typography.caption, fontSize = 14.sp )
                }
            }
        }
    }

    @Composable
    private fun BookImage() {
        Image(
            painter = painterResource(id = R.drawable.book_image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(corner = CornerSize(16.dp))),
            alignment = Alignment.BottomCenter
        )
    }
}
