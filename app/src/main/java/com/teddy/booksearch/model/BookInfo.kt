package com.teddy.booksearch.model

/**
 * used on Detail Screen
 */
data class BookInfo(
    val error: String,
    val title: String,
    val subTitle: String,
    val author: String,
    val isbn10: String,
    val isbn13: String,
    val pages: Int,
    val year: Int,
    val rating: Int,
    val desc: String,
    val price: String,
    val image: String,
    val url: String,
    val pdf: String
)
