package codelounge.app.com.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import codelounge.app.com.ViewModel.BottomNavViewModel
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.ViewModel.NavigationViewModel

@Composable
fun CombineScreen(
    firebaseViewModel: FirebaseViewModel = viewModel(),
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val topNavController = rememberNavController()
    val navBackStackEntry by topNavController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        firebaseViewModel.loadFirebaseData()
    }

    val firebaseData by firebaseViewModel.firebaseData.collectAsState()
    val selectedIndex by navigationViewModel.selectedIndex.collectAsState()

    Scaffold(
        topBar = {
            when (navBackStackEntry?.destination?.route) {
                "listContents/{title}/{content}" -> {
                    val title = navBackStackEntry?.arguments?.getString("title") ?: ""
                    ContentsAppBar(navController = topNavController, title = title)
                }
                else -> AppBar(selectedIndex = selectedIndex)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                items = BottomNavViewModel().items,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    navigationViewModel.selectIndex(index)
                    when (index) {
                        0 -> topNavController.navigate("lifeCycleList")
                        1 -> topNavController.navigate("kotlinList")
                        2 -> topNavController.navigate("swiftUIList")
                        3 -> topNavController.navigate("webList")
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = topNavController, startDestination = "lifeCycleList") {
                composable("lifeCycleList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it == "Algorithms" }
                    )
                }
                composable("kotlinList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it in listOf("Kotlin","Android Component") }
                    )
                }
                composable("swiftUIList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it in listOf("Swift","SwiftUI", "UIKit") }
                    )
                }
                composable("webList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it in listOf("HTML", "CSS", "JavaScript") }
                    )
                }
                composable(
                    route = "listContents/{title}/{content}",
                    arguments = listOf(
                        navArgument("title") { type = NavType.StringType },
                        navArgument("content") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val content = backStackEntry.arguments?.getString("content") ?: ""

                    ListContentsScreen(
                        title = title,
                        content = content
                    )
                }
            }
        }
    }

}
