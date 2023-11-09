package com.teddy.booksearch.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.search.SearchViewModel.UiEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onBookClicked: (isbn13: String) -> Unit
) {
    val books = viewModel.books.collectAsLazyPagingItems()

    SearchScreen(
        books = books,
        uiEvent = viewModel.uiEvent,
        onSearch = viewModel::search,
        onBookClicked = onBookClicked
    )
}

@Composable
fun SearchScreen(
    books: LazyPagingItems<Book>,
    uiEvent: SharedFlow<UiEvent>,
    onSearch: (String) -> Unit,
    onBookClicked: (isbn13: String) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEvent.collectLatest {
            when(it) {
                is UiEvent.QueryEmpty -> {
                    Toast.makeText(context, "query is empty", Toast.LENGTH_SHORT).show()
                }

                is UiEvent.InvalidQuery -> {
                    Toast.makeText(context, "invalid query format", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSearch = onSearch
        )
        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn() {
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
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = book.subtitle,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.LightGray
                    ),
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = book.price,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.LightGray
                    ),
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = book.url,
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.LightGray
                    ),
                    fontWeight = FontWeight.SemiBold
                )


            }
        }
    }

}