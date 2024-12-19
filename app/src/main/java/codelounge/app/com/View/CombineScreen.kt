// CombineScreen.kt
package codelounge.app.com.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import codelounge.app.com.ViewModel.BottomNavViewModel
import codelounge.app.com.ViewModel.FirebaseViewModel

@Composable
fun CombineScreen(
    navController: NavHostController,
    viewModel: FirebaseViewModel = viewModel()
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadFirebaseData()
    }

    val firebaseData by viewModel.firebaseData.collectAsState()

    val filteredData = remember(firebaseData, selectedIndex) {
        when (selectedIndex) {
            0 -> firebaseData.filterKeys { it == "Algorithms" }
            1 -> firebaseData.filterKeys { it == "Kotlin" }
            2 -> firebaseData.filterKeys { it in listOf("SwiftUI", "UIKit") }
            3 -> firebaseData.filterKeys { it in listOf("HTML", "CSS", "JavaScript") }
            else -> firebaseData // No filtering, use all data
        }
    }

    Scaffold(
        topBar = { AppBar(selectedIndex) },
        bottomBar = {
            BottomNavigationBar(
                items = BottomNavViewModel().items,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it },
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LifeCycleListScreen(
                navController = navController,
                firebaseData = filteredData,
                appbar = when (selectedIndex) {
                    0 -> "CS"
                    1 -> "Android"
                    2 -> "iOS"
                    3 -> "Frontend"
                    else -> "CS"
                }
            )
        }
    }
}