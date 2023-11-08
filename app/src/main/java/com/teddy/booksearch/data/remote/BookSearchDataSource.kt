package com.teddy.booksearch.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.teddy.booksearch.data.model.Book

class BookSearchDataSource(
    private val query: String,
    private val bookService: BookService
) : PagingSource<Int, Book>() {
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        try {
            val nextPage = params.key ?: 1
            val response = bookService.getSearchBookList(query = query, page = nextPage)
            val endOfPagination = response.books.isEmpty()
            return LoadResult.Page(
                data = response.books,
                prevKey =
                if (nextPage == 1) null
                else nextPage - 1,
                nextKey = if (endOfPagination) null else nextPage.plus(1)
            )

        } catch (t: Throwable) {
            return LoadResult.Error(t)
        }
    }
}