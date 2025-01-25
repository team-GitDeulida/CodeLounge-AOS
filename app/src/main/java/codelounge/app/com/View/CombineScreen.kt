package codelounge.app.com.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.BottomNavViewModel
import codelounge.app.com.ViewModel.FirebaseViewModel
import codelounge.app.com.ViewModel.NavigationViewModel

@Composable
fun CombineScreen(
    navController: NavHostController,
    firebaseViewModel: FirebaseViewModel = viewModel(),
    navigationViewModel: NavigationViewModel = viewModel()
) {
    val topNavController = rememberNavController()

    var searchQuery by rememberSaveable { mutableStateOf("") }
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
                "profile" -> {} // 프로필 화면에서는 topBar를 숨깁니다.
                else -> AppBar(selectedIndex = selectedIndex, onSearchQueryChanged = { searchQuery = it })
            }
        },
        bottomBar = {
            BottomNavigationBar(
                items = BottomNavViewModel().items,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    navigationViewModel.selectIndex(index)
                    when (index) {
                        0 -> topNavController.navigate("CSList")
                        1 -> topNavController.navigate("kotlinList")
                        2 -> topNavController.navigate("swiftUIList")
                        3 -> topNavController.navigate("profile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = topNavController, startDestination = "CSList") {
                composable("CSList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it == "Algorithms" },
                        searchQuery = searchQuery
                    )
                }
                composable("kotlinList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it in listOf("Kotlin", "Android Component", "Android Architecture", "Jetpack Compose UI") },
                        searchQuery = searchQuery
                    )
                }
                composable("swiftUIList") {
                    LifeCycleListScreen(
                        navController = topNavController,
                        firebaseData = firebaseData.filterKeys { it in listOf("Swift", "SwiftUI", "UIKit") },
                        searchQuery = searchQuery
                    )
                }
                composable("profile") {
                    ProfileScreen(navController)
                }
                composable("login") {
                    LoginScreen(navController = topNavController,LoginRepository())
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