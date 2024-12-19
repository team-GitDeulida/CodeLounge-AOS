package codelounge.app.com.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FirebaseViewModel(private val repository: FirebaseRepository) : ViewModel() {

    private val _firebaseData = MutableStateFlow<Map<String, Any?>>(emptyMap())
    val firebaseData: StateFlow<Map<String, Any?>> = _firebaseData

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var isDataLoaded = false

    fun loadFirebaseData() {
        if (isDataLoaded) {
            Log.d("FirebaseViewModel", "Data already loaded, using cached data")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            Log.d("FirebaseViewModel", "Loading data from Firebase")
            repository.fetchFirebaseData()
                .catch { exception ->
                    _errorMessage.value = exception.message
                    Log.e("FirebaseViewModel", "Error fetching data: ${exception.message}")
                }
                .collect { data ->
                    _firebaseData.value = data
                    _isLoading.value = false
                    isDataLoaded = true
                }
        }
    }
}