package com.teddy.booksearch.data.repository

interface BookRepository {
    fun searchBooks(page: Int, query: String)
    fun getBookDetail(isbn13: String)
}