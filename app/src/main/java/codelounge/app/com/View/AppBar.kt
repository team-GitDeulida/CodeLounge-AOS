package codelounge.app.com.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(selectedIndex: Int) {
    val title = when (selectedIndex) {
        0 -> "CS"
        1 -> "Android"
        2 -> "iOS"
        3 -> "Frontend"
        else -> "CS"
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = WhiteTextColor,
        ),
        title = {
            Row {
                Column { Text(title) }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    )
}