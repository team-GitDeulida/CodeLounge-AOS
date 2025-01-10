package codelounge.app.com.ViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Web
import androidx.lifecycle.ViewModel
import codelounge.app.com.Model.BottomNavItem

class BottomNavViewModel : ViewModel() {
    val items = listOf(
        BottomNavItem(label = "CS", icon = Icons.Filled.School, route = "home"), // Computer Science 관련 아이콘
        BottomNavItem(label = "Android", icon = Icons.Filled.Android, route = "android"), // Android 아이콘
        BottomNavItem(label = "iOS", icon = Icons.Filled.PhoneIphone, route = "ios"), // iOS 아이콘
        BottomNavItem(label = "Profile", icon = Icons.Filled.AccountCircle, route = "profile") // Profile 관련 아이콘
    )
}