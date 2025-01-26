package codelounge.app.com.View

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.ViewModel.IntroViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel,
    loginRepository: LoginRepository,
    introViewModel: IntroViewModel
) {
    NavHost(navController = navController, startDestination = "first") {
        composable("first") {
            Introscreen(navController, introViewModel)
        }
        composable("login") {
            LoginScreen(navController, loginRepository)
        }
        composable("home") {
            CombineScreen(navController, firebaseViewModel)
        }
        composable("signIn") {
            SignInDetails(navController, loginRepository)
        }
        composable("ChangeProfile") {
            ChangeProfileScreen(navController, loginRepository)
        }
    }
}