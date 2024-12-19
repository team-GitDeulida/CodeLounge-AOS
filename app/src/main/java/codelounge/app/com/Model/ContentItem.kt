package codelounge.app.com.Model

import android.util.Log

data class ContentItem(
    val title: String,
    val content: String,
    val parent: String
) {
    init {
        Log.d("ContentItem", "title: $title, content: $content, parent: $parent")
    }
}
