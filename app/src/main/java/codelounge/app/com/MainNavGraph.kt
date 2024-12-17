package codelounge.app.com

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType

@Composable
fun MainNavGraph(navController: NavHostController, firebaseData: Map<String, Any?>) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { loginActivity(navController) }
        composable("home") { Combine(navController, firebaseData) }
        composable(
            "listContents/{title}/{content}/{appbar}/{parent}",
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
            ListContents(navController, title, content, appbar, parent)
        }
    }
}