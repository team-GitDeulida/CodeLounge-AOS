package codelounge.app.com

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import codelounge.app.com.ui.theme.CodeLoungeTheme

@Composable
fun LifecycleList(modifier: Modifier = Modifier, navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycleState.value = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    LazyColumn {
        item {
            Text("AOS",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            AosList(navController = navController)
        }

        item {
            Text("Rx",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            RxList(navController = navController)
        }

        item {
            Text("Coruntine",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            CoruntineList(navController = navController)
        }
    }
}

@Composable
fun AosDialog(showDialog: MutableState<Boolean>,navController: NavController) {
    if (showDialog.value) {
        ListContents(navController = navController)

    }
}


@Composable
fun AosList(navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    AosDialog(showDialog = showDialog, navController)

    Column(modifier = Modifier.padding(16.dp)) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { navController.navigate("listContents") },
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp) // Add top rounding
            ) {
                Text(
                    text = "Activity에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { /* Handle click event for Activity lifecycle */ },
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp) // Add bottom rounding
            ) {
                Text(
                    text = "Activity lifecycle에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
}


@Composable
fun RxList(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { navController.navigate("listContents") },
                shape = RectangleShape // Remove card rounding
            ) {
                Text(
                    text = "Activity에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { /* Handle click event for Activity lifecycle */ },
                shape = RectangleShape // Remove card rounding
            ) {
                Text(
                    text = "Activity lifecycle에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun CoruntineList(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { navController.navigate("listContents") },
                shape = RectangleShape // Remove card rounding
            ) {
                Text(
                    text = "Activity에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clickable { /* Handle click event for Activity lifecycle */ },
                shape = RectangleShape // Remove card rounding
            ) {
                Text(
                    text = "Activity lifecycle에 대해서 설명해주세요.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ListPreview(){
    CodeLoungeTheme {
        LifecycleList(navController = NavController(
            context = TODO()
        ))
    }
}
