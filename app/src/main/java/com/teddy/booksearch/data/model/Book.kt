package com.teddy.booksearch.data.model

/**
 * used on Search Screen
 */
data class Book(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String,
)

fun getDummyBook() = Book(
    title = "test",
    subtitle = "test2",
    isbn13 = "123",
    price = "123원",
    image = "https://itbook.store/img/books/9781484206485.png",
    url = "https://itbook.store/img/books/9781484206485"
)
