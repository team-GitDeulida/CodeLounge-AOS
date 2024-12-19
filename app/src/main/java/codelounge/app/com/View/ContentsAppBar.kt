package codelounge.app.com.View

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentsAppBar(navController: NavController, appbar: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = WhiteTextColor,
        ),
        title = { Text(appbar) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}