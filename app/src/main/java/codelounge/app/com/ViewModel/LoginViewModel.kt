package codelounge.app.com.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginStatus = MutableStateFlow<String>("")
    val loginStatus: StateFlow<String> get() = _loginStatus

    fun googleLogin() {
        viewModelScope.launch {
            val result = repository.performGoogleLogin()
            _loginStatus.value = if (result) "Google 로그인 성공" else "Google 로그인 실패"
        }
    }

    fun githubLogin() {
        viewModelScope.launch {
            val result = repository.performGithubLogin()
            _loginStatus.value = if (result) "Github 로그인 성공" else "Github 로그인 실패"
        }
    }
}