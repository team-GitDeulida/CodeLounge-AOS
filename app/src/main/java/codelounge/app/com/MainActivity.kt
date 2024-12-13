package codelounge.app.com

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import codelounge.app.com.ui.theme.BackgroundColor
import codelounge.app.com.ui.theme.CodeLoungeTheme
import codelounge.app.com.ui.theme.WhiteTextColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeLoungeTheme {
                val navController = rememberNavController()
                MainNavGraph(navController = navController)
            }
        }
    }
}

@Composable
fun Combine(navController: NavController) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { AppBar() },
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedIndex = selectedIndex.value,
                onItemSelected = { selectedIndex.value = it }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LifecycleList(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(){
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundColor,
                    titleContentColor = WhiteTextColor,
                ),
                title = {
                    Row {
                        Column {
                            Text("Android")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                },
            )
}


@Preview(showBackground = true)
@Composable
fun AppBarPreview(){
    CodeLoungeTheme {
        AppBar()
    }
}


@Preview(showBackground = true)
@Composable
fun CombinePreview(){
    CodeLoungeTheme {
        Combine(navController = rememberNavController())
    }
}


