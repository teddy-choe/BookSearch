package com.teddy.booksearch.data.model

data class SearchResponse(
    val total: Int,
    val page: Int,
    val books: List<Book>
)
