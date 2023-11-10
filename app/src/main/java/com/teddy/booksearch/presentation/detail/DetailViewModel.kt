package com.teddy.booksearch.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teddy.booksearch.data.model.BookInfo
import com.teddy.booksearch.data.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _bookInfo: MutableStateFlow<BookInfo?> = MutableStateFlow(null)
    val bookInfo: StateFlow<BookInfo?> = _bookInfo.asStateFlow()

    private val navArgs: String = savedStateHandle["isbn13"] ?: ""

    init {
        getBookInfo(navArgs)
    }

    private fun getBookInfo(isbn13: String) {
        viewModelScope.launch {
            runCatching {
                bookRepository.getBookDetail(isbn13)
            }.onSuccess { result ->
                if (result.error == "0") {
                    _bookInfo.update {
                        result
                    }

                    _uiState.update {
                        UiState.Success
                    }
                } else {
                    _uiState.update {
                        UiState.Error
                    }
                }
            }.onFailure {
                _uiState.update {
                    UiState.Error
                }
            }
        }
    }

    sealed interface UiState {
        object Loading : UiState
        object Success : UiState
        object Error : UiState
    }
}