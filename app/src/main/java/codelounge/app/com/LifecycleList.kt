package codelounge.app.com

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import codelounge.app.com.ui.theme.CodeLoungeTheme
import codelounge.app.com.ui.theme.CustomGreenColor

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
            Text(
                "AOS",
                style = MaterialTheme.typography.bodyLarge.copy(color = CustomGreenColor),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            AosList(navController = navController)
        }
        item {
            Text(
                "Rx",
                style = MaterialTheme.typography.bodyLarge.copy(color = CustomGreenColor),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            RxList(navController = navController)
        }
        item {
            Text(
                "Coruntine",
                style = MaterialTheme.typography.bodyLarge.copy(color = CustomGreenColor),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            CoruntineList(navController = navController)
        }
    }
}

@Composable
fun AosList(navController: NavController) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Activity에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { navController.navigate("listContents") }
        )
        HorizontalDivider()
        Text(
            text = "Activity lifecycle에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


@Composable
fun RxList(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Activity에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { navController.navigate("listContents") }
        )
        HorizontalDivider()
        Text(
            text = "Activity lifecycle에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    }


@Composable
fun CoruntineList(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Activity에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { navController.navigate("listContents") }
        )
        HorizontalDivider()
        Text(
            text = "Activity lifecycle에 대해서 설명해주세요.",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    CodeLoungeTheme {
        // NavController를 적절히 초기화해야 합니다.
        // 예시로, NavController를 null로 초기화하고 필요에 따라 수정할 수 있습니다.
        val navController = NavController(context = LocalContext.current)
        LifecycleList(navController = navController)
    }
}

