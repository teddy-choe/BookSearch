package com.teddy.booksearch.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.teddy.booksearch.data.model.BookInfo
import java.lang.reflect.Type

class PdfSerializer : JsonDeserializer<BookInfo.PdfList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BookInfo.PdfList {
        val pdfJsonObject = json?.asJsonObject

        checkNotNull(pdfJsonObject) {"pdf json element is null"}

        val pdfs = mutableListOf<BookInfo.Pdf>()

        for (key in pdfJsonObject.keySet()) {
            pdfs.add(BookInfo.Pdf(key, pdfJsonObject.get(key).asString))
        }

        return BookInfo.PdfList(pdfs)
    }
}