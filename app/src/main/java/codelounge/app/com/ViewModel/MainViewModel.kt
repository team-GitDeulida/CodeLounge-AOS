package codelounge.app.com.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _firebaseData = MutableStateFlow<Map<String, Any?>>(emptyMap())
    val firebaseData: StateFlow<Map<String, Any?>> = _firebaseData

    // 비동기적으로 Firebase 데이터 로드
    fun loadData() {
        viewModelScope.launch {
            val data = repository.fetchFirebaseData().first()
            _firebaseData.value = data
        }
    }

    // 특정 데이터를 필터링하는 메서드 (필요할 경우)
    fun getFilteredData(filterKey: String): Map<String, Any?> {
        return _firebaseData.value.filterKeys { it == filterKey }
    }
}