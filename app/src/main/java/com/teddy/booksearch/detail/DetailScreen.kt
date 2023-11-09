package com.teddy.booksearch.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.teddy.booksearch.data.model.BookInfo

@Composable
fun DetailRoute(viewModel: DetailViewModel = hiltViewModel()) {
    val bookInfo = viewModel.bookInfo.collectAsState().value
    DetailScreen(bookInfo)
}

@Composable
fun DetailScreen(
    bookInfo: BookInfo?
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        bookInfo?.let {
            AsyncImage(
                model = bookInfo.image, contentDescription = "bookInfo",
                modifier = Modifier
                    .padding(8.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = bookInfo.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            ContentWithHeader(header = "SubTitle", content = bookInfo.subtitle)
            ContentWithHeader(header = "Author", content = bookInfo.author)
            ContentWithHeader(header = "Page", content = bookInfo.pages.toString())
            ContentWithHeader(header = "Year", content = bookInfo.year.toString())
            ContentWithHeader(header = "Rating", content = bookInfo.rating.toString())
            ContentWithHeader(header = "Description", content = bookInfo.desc)
            ContentWithHeader(header = "Price", content = bookInfo.price)

            bookInfo.pdfList?.let { list ->
                list.pdfs.forEach { pdf ->
                    ContentWithHeader(header = pdf.title, content = pdf.url)
                }

            }
        }

    }
}

@Composable
fun ContentWithHeader(header: String, content: String?) {
    content?.let {
        Text(
            text = header,
            modifier = Modifier
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black
            ),
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = content,
            modifier = Modifier
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Black
            ),
            fontWeight = FontWeight.Light
        )
    }

}