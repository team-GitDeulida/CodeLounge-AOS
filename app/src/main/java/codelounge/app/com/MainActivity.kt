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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        database = FirebaseDatabase.getInstance().reference
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
        topBar = { AppBar(selectedIndex.value) },
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedIndex = selectedIndex.value,
                onItemSelected = { selectedIndex.value = it }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (selectedIndex.value) {
                0 -> CsLifecycleList()
                1 -> AndroidLifecycleList(navController = navController)
                2 -> IosLiftecycleList()
                3 -> FrontLifecycleList()
                else -> CsLifecycleList()
            }
        }
    }
}

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
                Column {
                    Text(title)
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
        AppBar(1)
    }
}


@Preview(showBackground = true)
@Composable
fun CombinePreview(){
    CodeLoungeTheme {
        Combine(navController = rememberNavController())
    }
}


