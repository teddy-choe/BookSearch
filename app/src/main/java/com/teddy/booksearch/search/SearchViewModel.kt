package com.teddy.booksearch.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.data.model.getDummyBook
import com.teddy.booksearch.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val bookRepository: BookRepository
) : ViewModel() {
    private val _books: MutableStateFlow<PagingData<Book>> = MutableStateFlow(PagingData.empty())
    val books: StateFlow<PagingData<Book>> = _books.asStateFlow()

    fun search(query: String) {
        viewModelScope.launch {
            bookRepository.searchBooks(query)
                .cachedIn(viewModelScope)
                .collect { result ->
                    _books.update {
                        result
                    }
                }
        }
    }
}