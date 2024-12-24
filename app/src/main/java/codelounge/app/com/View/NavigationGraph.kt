package codelounge.app.com.View

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.FirebaseViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel,
    loginRepository: LoginRepository
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("login") {
            LoginScreen(navController, loginRepository)
        }
        composable("home") {
            CombineScreen(firebaseViewModel)
        }
    }
}