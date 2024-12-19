package codelounge.app.com.Model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val label: String,
    val route: Any
)