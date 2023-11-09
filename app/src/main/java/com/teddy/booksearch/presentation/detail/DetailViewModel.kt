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
): ViewModel() {
    private val _bookInfo: MutableStateFlow<BookInfo?> = MutableStateFlow(null)
    val bookInfo: StateFlow<BookInfo?> = _bookInfo.asStateFlow()

    private val navArgs: String = savedStateHandle["isbn13"] ?: ""

    init {
        getBookInfo(navArgs)
    }

    private fun getBookInfo(isbn13: String) {
        viewModelScope.launch {
            val result = bookRepository.getBookDetail(isbn13)
            _bookInfo.update {
                result
            }
        }
    }
}