package codelounge.app.com.View

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.ViewModel.ListContentsViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel,
    loginRepository: LoginRepository
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, loginRepository)
        }
        composable("home") {
            CombineScreen(navController, firebaseViewModel)
        }
        composable(
            route = "listContents/{title}/{content}/{appbar}/{parent}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("content") { type = NavType.StringType },
                navArgument("appbar") { type = NavType.StringType },
                navArgument("parent") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val content = backStackEntry.arguments?.getString("content") ?: ""
            val appbar = backStackEntry.arguments?.getString("appbar") ?: ""
            val parent = backStackEntry.arguments?.getString("parent") ?: ""


            ListContentsScreen(navController = navController, appbar = appbar, title = title, content = content, parent = parent)
        }
    }
}