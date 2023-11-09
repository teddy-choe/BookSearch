package com.teddy.booksearch

import com.teddy.booksearch.util.QueryType
import com.teddy.booksearch.util.checkSearchType
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SearchRegexTest {
    /**
     * 'abc', 'abc|abc', 'abc-abc'의 형태가 아닌 문자열의 경우 에러를 발생시킨다.
     */
    @Test
    fun search_incorrect_query() {
        val testQuery = "test||test"
        val result = testQuery.checkSearchType()
        assertThat(result, instanceOf(QueryType.INVALID.javaClass))

        val testQuery2 = "test+test"
        val result2 = testQuery2.checkSearchType()
        assertThat(result2, instanceOf(QueryType.INVALID.javaClass))

        val testQuery3 = "test-test-test"
        val result3 = testQuery3.checkSearchType()
        assertThat(result3, instanceOf(QueryType.INVALID.javaClass))
    }
}