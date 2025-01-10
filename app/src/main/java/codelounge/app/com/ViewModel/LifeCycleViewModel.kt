package codelounge.app.com.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.FirebaseItem
import codelounge.app.com.Model.FirebaseSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LifeCycleViewModel : ViewModel() {

    private val _firebaseSections = MutableStateFlow<List<FirebaseSection>>(emptyList())
    val firebaseSections: StateFlow<List<FirebaseSection>> = _firebaseSections

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadData(firebaseData: Map<String, Any?>) {
        viewModelScope.launch {
            _isLoading.value = true // 로딩 시작
            val sections = firebaseData.mapNotNull { (key, value) ->
                val nestedData = value as? Map<String, Map<String, Any?>>
                nestedData?.let {
                    FirebaseSection(
                        sectionTitle = key,
                        items = it.values.map { item ->
                            FirebaseItem(
                                title = item["title"] as? String ?: "",
                                content = item["content"] as? String ?: ""
                            )
                        }
                    )
                }
            }
            _firebaseSections.value = sections
            _isLoading.value = false // 로딩 종료
        }
    }
}
