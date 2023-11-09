package com.teddy.booksearch.util

val searchRegex = """\w+""".toRegex()
val orRegex = """\w+\|\w+$""".toRegex()
val minusRegex = """\w+-\w+$""".toRegex()

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