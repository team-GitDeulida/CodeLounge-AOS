package codelounge.app.com.ViewModel

import androidx.lifecycle.ViewModel
import codelounge.app.com.Model.ContentItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListContentsViewModel : ViewModel() {
    private val _contentItem = MutableStateFlow<ContentItem?>(null)
    val contentItem: StateFlow<ContentItem?> = _contentItem

    fun setContentItem(title: String, content: String,) {
        if (_contentItem.value == null) {
            _contentItem.value = ContentItem(title, content)
        }
    }
}




