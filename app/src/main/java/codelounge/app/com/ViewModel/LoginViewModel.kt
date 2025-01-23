package codelounge.app.com.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import codelounge.app.com.Model.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userId: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class SignInState {
    object Idle : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    data class Error(val message: String) : SignInState()
}

data class User(
    val id: String,
    val nickname: String?,
    val birthday: String?,
    val gender: String?,
    val registerDate: String?
)

sealed class NavigationState {
    object Idle : NavigationState()
    object NavigateToSignIn : NavigationState()
    object NavigateToHome : NavigationState()
}

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState: StateFlow<NavigationState> = _navigationState

    fun googleLogin(account: GoogleSignInAccount,onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val userId = repository.googleSignIn(account)
            if (userId != null) {
                repository.saveUserToDatabaseIfNotExists(userId, "google")
                fetchUserData(userId)
                _loginState.value = LoginState.Success(userId)
                onSuccess(userId)
            } else {
                _loginState.value = LoginState.Error("Google 로그인 실패")
            }
        }
    }

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            val userData = repository.fetchUserData(userId)
            if (userData != null) {
                _user.value = User(
                    id = userId,
                    nickname = userData["nickname"] as? String,
                    birthday = userData["birthdayDate"] as? String,
                    gender = userData["gender"] as? String,
                    registerDate = userData["registerDate"] as? String
                )
            }
            println("User data fetched: $userData")
        }
    }

    fun saveUserData(nickname: String, birthdate: String, gender: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            val userId = FirebaseAuth.getInstance().currentUser?.uid!!
            if (userId != null) {
                val user = mapOf(
                    "nickname" to nickname,
                    "birthdayDate" to birthdate,
                    "gender" to gender,
                    "registerDate" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA).format(
                        Date()
                    )
                )
                val success = repository.saveUserData(userId, user)
                if (success) {
                    fetchUserData(userId)
                    _signInState.value = SignInState.Success
                } else {
                    _signInState.value = SignInState.Error("데이터 저장 실패")
                }
            }
        }
    }

    fun checkUserData(userId: String) {
        viewModelScope.launch {
            val userData = repository.fetchUserData(userId)
            if (userData != null) {
                val birthdayDate = userData["birthdayDate"] as? String
                val gender = userData["gender"] as? String
                val nickname = userData["nickname"] as? String

                if (birthdayDate.isNullOrEmpty() || gender.isNullOrEmpty() || nickname.isNullOrEmpty()) {
                    _navigationState.value = NavigationState.NavigateToSignIn
                } else {
                    _navigationState.value = NavigationState.NavigateToHome
                }
            } else {
                _navigationState.value = NavigationState.NavigateToSignIn
            }
        }
    }
    fun resetNavigationState() {
        _navigationState.value = NavigationState.Idle
    }
}
