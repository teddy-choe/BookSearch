package com.teddy.booksearch

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.teddy.booksearch.model.Book

@Composable
fun SearchRoute() {
    SearchScreen()
}

@Composable
fun SearchScreen(
    list: List<Book>
) {
    LazyColumn() {
        items(list) {

        }
    }
}