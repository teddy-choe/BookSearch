package com.teddy.booksearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.teddy.booksearch.model.Book

@Composable
fun SearchRoute() {
    SearchScreen(listOf())
}

@Composable
fun SearchScreen(
    list: List<Book>
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onSearch = {}
    )
    Spacer(modifier = Modifier.height(24.dp))
    LazyColumn() {
        items(list) {

        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = { onSearch(text) }
            ),
            maxLines = 1,
            singleLine = true,
            modifier = modifier
                .background(Color.LightGray, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused == false
                }
        )

        if (isHintDisplayed) {
            Text(
                text = "Search",
                modifier = modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}