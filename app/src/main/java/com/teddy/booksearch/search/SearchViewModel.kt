package com.teddy.booksearch.search

import androidx.lifecycle.ViewModel
import com.teddy.booksearch.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun search(query: String) {
        // Usecase를 호출해서 상태를 변경하거나, 에러를 처리하거나?

    }

    sealed interface UiState {
        object Loading : UiState
        data class Success(
            val books: List<Book>
        ) : UiState

        data class Error(
            val throwable: Throwable
        ) : UiState
    }
}