package com.teddy.booksearch.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.search.SearchViewModel.UiState

@Composable
fun SearchRoute(viewModel: SearchViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value
    SearchScreen(
        uiState = uiState,
        onSearch = viewModel::search
        )
}

@Composable
fun SearchScreen(
    uiState: UiState,
    onSearch: (String) -> Unit
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onSearch = onSearch
    )
    Spacer(modifier = Modifier.height(24.dp))

    when (uiState) {
        is UiState.Success -> {
            LazyColumn() {
                items(uiState.books) { book ->
                    BookItem(
                        book = book
                    )
                }
            }

        }

        else -> {

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

@Composable
fun BookItem(book: Book) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Row(
            Modifier
                .padding(all = 4.dp)
                .fillMaxSize()
        ) {
            AsyncImage(
                model = book.image,
                contentDescription = "book's image",
                modifier = Modifier
                    .padding(8.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = book.subTitle,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.LightGray
                    ),
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.6.sp
                )
            }
        }
    }

}