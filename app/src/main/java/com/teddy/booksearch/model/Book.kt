package com.teddy.booksearch.model

/**
 * used on Search Screen
 */
data class Book(
    val title: String,
    val subTitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String,
)
