package com.teddy.booksearch.presentation.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.presentation.search.SearchViewModel.UiEvent
import com.teddy.booksearch.presentation.search.SearchViewModel.UiState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onBookClicked: (isbn13: String) -> Unit
) {
    val books = viewModel.books.collectAsLazyPagingItems()
    val uiState = viewModel.uiState.collectAsState().value

    SearchScreen(
        books = books,
        uiState = uiState,
        uiEvent = viewModel.uiEvent,
        onSearch = viewModel::search,
        onBookClicked = onBookClicked
    )
}

@Composable
fun SearchScreen(
    books: LazyPagingItems<Book>,
    uiState: UiState,
    uiEvent: SharedFlow<UiEvent>,
    onSearch: (String) -> Unit,
    onBookClicked: (isbn13: String) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEvent.collectLatest {
            when (it) {
                is UiEvent.InvalidQuery -> {
                    Toast.makeText(
                        context,
                        "invalid query format. query must be alphabet or number.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSearch = onSearch
        )

        when (uiState) {
            is UiState.Before -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "search books!",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            is UiState.Success -> {
                if (books.itemCount == 0) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "result is empty.",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                } else {
                    LazyColumn {
                        items(books.itemCount) { index ->
                            books[index]?.let {
                                BookItem(
                                    book = it,
                                    onBookClicked = onBookClicked
                                )
                            }
                        }
                    }

                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "error...",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }
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
        val context = LocalContext.current

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    if (text.isEmpty()) {
                        Toast.makeText(context, "query is empty", Toast.LENGTH_SHORT).show()
                    } else {
                        onSearch(text)
                    }
                }
            ),
            maxLines = 1,
            singleLine = true,
            modifier = modifier
                .shadow(1.dp)
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
fun BookItem(
    book: Book,
    onBookClicked: (isbn13: String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.background)
            .shadow(1.dp)
            .clickable {
                onBookClicked(book.isbn13)
            }
    ) {
        Row(Modifier.padding(all = 4.dp)) {
            AsyncImage(
                model = book.image,
                contentDescription = "book's image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(8.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                ContentText(book.subtitle)

                ContentText(book.price)

                ContentText(book.url)
            }
        }
    }
}

@Composable
fun ContentText(content: String) {
    if (content.isNotEmpty()) {
        Text(
            text = content,
            modifier = Modifier
                .padding(vertical = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.LightGray
            ),
            fontWeight = FontWeight.SemiBold
        )
    }


}