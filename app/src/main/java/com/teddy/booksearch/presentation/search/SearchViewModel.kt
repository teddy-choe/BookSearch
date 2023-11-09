package com.teddy.booksearch.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.teddy.booksearch.data.model.Book
import com.teddy.booksearch.data.repository.BookRepository
import com.teddy.booksearch.util.QueryType
import com.teddy.booksearch.util.QueryType.EMPTY
import com.teddy.booksearch.util.QueryType.INVALID
import com.teddy.booksearch.util.QueryType.MINUS
import com.teddy.booksearch.util.QueryType.ONLY_WORDS
import com.teddy.booksearch.util.QueryType.OR
import com.teddy.booksearch.util.checkSearchType
import com.teddy.booksearch.util.minusRegex
import com.teddy.booksearch.util.orRegex
import com.teddy.booksearch.util.searchRegex
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

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    fun search(query: String) {
        viewModelScope.launch {
            when(query.checkSearchType()) {
                ONLY_WORDS -> {
                    bookRepository.searchBooks(query)
                        .cachedIn(viewModelScope)
                        .collect { result ->
                            _books.update {
                                result
                            }

                            _uiState.update {
                                UiState.Success
                            }
                        }
                }

                OR -> {
                    val split = query.split("|")
                    val preQuery = split[0]
                    val postQuery = split[1]

                    bookRepository.searchBooks(preQuery, postQuery)
                        .cachedIn(viewModelScope)
                        .collect { result ->
                            _books.update {
                                result
                            }

                            _uiState.update {
                                UiState.Success
                            }
                        }

                }

                MINUS -> {
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

                            _uiState.update {
                                UiState.Success
                            }
                        }
                }

                EMPTY -> {
                    _uiEvent.emit(UiEvent.QueryEmpty)
                    _uiState.update {
                        UiState.Empty
                    }
                }

                INVALID -> {
                    _uiEvent.emit(UiEvent.InvalidQuery)
                    _uiState.update {
                        UiState.Error
                    }
                }
            }
        }
    }

    sealed interface UiState {
        object Empty: UiState
        object Error: UiState
        object Success: UiState
    }

    sealed interface UiEvent {
        object QueryEmpty: UiEvent
        object InvalidQuery: UiEvent
    }
}