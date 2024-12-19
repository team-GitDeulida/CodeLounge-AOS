// LifeCycleViewModel.kt
package codelounge.app.com.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.FirebaseItem
import codelounge.app.com.Model.FirebaseSection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LifeCycleViewModel : ViewModel() {

    private val _firebaseSections = MutableStateFlow<List<FirebaseSection>>(emptyList())
    val firebaseSections: StateFlow<List<FirebaseSection>> = _firebaseSections

    fun loadData(firebaseData: Map<String, Any?>) {
        viewModelScope.launch {
            Log.d("LifeCycleViewModel", "Received firebaseData: $firebaseData")
            val sections = firebaseData.mapNotNull { (key, value) ->
                Log.d("LifeCycleViewModel", "Processing section: $key with value: $value")
                val nestedData = value as? Map<String, Map<String, Any?>>
                nestedData?.let {
                    FirebaseSection(
                        sectionTitle = key,
                        items = it.values.map { item ->
                            Log.d("LifeCycleViewModel", "Processing item: $item")
                            FirebaseItem(
                                title = item["title"] as? String ?: "",
                                content = item["content"] as? String ?: ""
                            )
                        }
                    )
                }
            }
            _firebaseSections.value = sections
            Log.d("LifeCycleViewModel", "Loaded sections: $sections")
        }
    }
}