package com.teddy.booksearch.data.model

import com.google.gson.annotations.SerializedName

/**
 * used on Detail Screen
 */
data class BookInfo(
    @SerializedName("error") val error: String,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("author") val author: String,
    @SerializedName("isbn10") val isbn10: String,
    @SerializedName("isbn13") val isbn13: String,
    @SerializedName("pages") val pages: Int,
    @SerializedName("year") val year: Int,
    @SerializedName("rating") val rating: Int,
    @SerializedName("desc") val desc: String,
    @SerializedName("price") val price: String,
    @SerializedName("image") val image: String,
    @SerializedName("url") val url: String,
    @SerializedName("pdf") val pdfList: PdfList?
) {
    data class PdfList(
        val pdfs: List<Pdf>
    )

    data class Pdf(
        val title: String,
        val url: String
    )
}
