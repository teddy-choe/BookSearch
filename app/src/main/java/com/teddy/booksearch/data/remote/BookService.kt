package com.teddy.booksearch.data.remote

import com.teddy.booksearch.data.model.BookInfo
import com.teddy.booksearch.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {
    @GET("search/{query}/{page}")
    suspend fun getSearchBookList(
        @Path("query") query: String,
        @Path("page") page: Int
    ): SearchResponse

    @GET("books/{isbn13}")
    suspend fun getBookDetail(
        @Path("isbn13") isbn13: String
    ): BookInfo
}