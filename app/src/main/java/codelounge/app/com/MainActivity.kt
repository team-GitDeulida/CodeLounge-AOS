package codelounge.app.com

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import codelounge.app.com.Model.FirebaseRepository
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.View.NavigationGraph
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.ViewModel.FirebaseViewModelFactory
import codelounge.app.com.theme.CodeLoungeTheme
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseReference = FirebaseDatabase.getInstance().reference
        val repository = FirebaseRepository(databaseReference)
        setContent {
            CodeLoungeTheme {
                val navController = rememberNavController()
                val viewModel: FirebaseViewModel = viewModel(
                    factory = FirebaseViewModelFactory(repository)
                )
                val loginRepository = LoginRepository()

                CoroutineScope(Dispatchers.IO).launch {
                    MobileAds.initialize(this@MainActivity) {}
                }

                LaunchedEffect(Unit) {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (currentUser != null) {
                        // 사용자가 로그인되어 있으면 home 화면으로 이동
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }

                NavigationGraph(
                    navController = navController,
                    firebaseViewModel = viewModel,
                    loginRepository = loginRepository,
                    introViewModel = viewModel()
                )
            }
        }
    }
}