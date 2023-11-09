package com.teddy.booksearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.data.remote.BookSearchDataSource
import com.teddy.booksearch.data.remote.BookService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val bookService: BookService
) {
    fun searchBooks(vararg query: String): Flow<PagingData<Book>> {
        val config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            prefetchDistance = 10
        )

        return Pager(config) {
            BookSearchDataSource(queryList = query, bookService = bookService)
        }.flow
    }

    suspend fun getBookDetail(isbn13: String) =
        bookService.getBookDetail(isbn13)
}