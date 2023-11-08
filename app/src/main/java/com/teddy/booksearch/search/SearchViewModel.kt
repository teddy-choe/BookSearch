package com.teddy.booksearch.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.data.repository.BookRepository
import com.teddy.booksearch.util.minusRegex
import com.teddy.booksearch.util.orRegex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {
    private val _books: MutableStateFlow<PagingData<Book>> = MutableStateFlow(PagingData.empty())
    val books: StateFlow<PagingData<Book>> = _books.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _uiEvent.emit(UiEvent.QueryEmpty)
                return@launch
            }

            if (orRegex.matches(query)) {
                val split = query.split("|")
                val preQuery = split[0]
                val postQuery = split[1]

                bookRepository.searchBooks(preQuery, postQuery)
                    .cachedIn(viewModelScope)
                    .collect { result ->
                        _books.update {
                            result
                        }
                    }
            } else if (minusRegex.matches(query)) {
                val split = query.split("-")
                val preQuery = split[0]
                val postQuery = split[1]

                bookRepository.searchBooks(preQuery)
                    .map { result ->
                        result.filter {
                            !it.title.lowercase().contains(postQuery.lowercase())
                        }
                    }
                    .cachedIn(viewModelScope)
                    .collect { filteredResult ->
                        _books.update {
                            filteredResult
                        }
                    }
            } else {
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

    sealed interface UiEvent {
        object QueryEmpty: UiEvent
    }
}