package com.teddy.booksearch

import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel()
    }

    /**
     * 'abc', 'abc|abc', 'abc-abc'의 형태가 아닌 문자열의 경우 에러를 발생시킨다.
     */
    @Test
    fun search_incorrect_query1() {
        var uiState = viewModel.uiState.value
        viewModel.search("test||test")
        assertThat(uiState, instanceOf(SearchViewModel.UiState.Error::class.java))
    }

    @Test
    fun search_incorrect_query2() {
        var uiState = viewModel.uiState.value
        viewModel.search("test+test")
        assertThat(uiState, instanceOf(SearchViewModel.UiState.Error::class.java))
    }
}