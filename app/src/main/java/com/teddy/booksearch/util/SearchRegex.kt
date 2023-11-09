package com.teddy.booksearch.util

val searchRegex = """[a-zA-Z0-9]+""".toRegex()
val orRegex = """[a-zA-Z0-9]+\|[a-zA-Z0-9]+$""".toRegex()
val minusRegex = """[a-zA-Z0-9]+-[a-zA-Z0-9]+$""".toRegex()

internal enum class QueryType {
    ONLY_WORDS, OR, MINUS, EMPTY, INVALID
}

internal fun String.checkSearchType(): QueryType {
    return if (searchRegex.matches(this)) QueryType.ONLY_WORDS
    else if (orRegex.matches(this)) QueryType.OR
    else if (minusRegex.matches(this)) QueryType.MINUS
    else if (this.isEmpty()) QueryType.EMPTY
    else QueryType.INVALID
}