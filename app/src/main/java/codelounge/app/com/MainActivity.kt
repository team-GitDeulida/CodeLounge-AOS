package codelounge.app.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import codelounge.app.com.Model.FirebaseRepository
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.View.NavigationGraph
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.theme.CodeLoungeTheme
import codelounge.app.com.ViewModel.FirebaseViewModelFactory
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
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