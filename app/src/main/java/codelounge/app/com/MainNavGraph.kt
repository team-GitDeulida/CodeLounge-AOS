package codelounge.app.com

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MainNavGraph(navController: NavHostController, firebaseData: Map<String, Any?>) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { loginActivity(navController) }
        composable("home") { Combine(navController,firebaseData) }
        composable("listContents") { ListContents(navController) }
    }
}