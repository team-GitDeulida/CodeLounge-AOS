package codelounge.app.com

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import codelounge.app.com.ui.theme.CodeLoungeTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ListContents(navController: NavController) {
    Scaffold(
        topBar = { ContentsAppBar(onBackClick = { navController.popBackStack() }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        Contents()
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentsAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row {
                Column {
            Text("개발의 정석")
                Text("Android", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun Contents() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Activity에 대해서 설명해주세요.",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "AOS",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = "Activity는 안드로이드 앱의 구성 요소 중 하나로, 사용자 인터페이스를 제공합니다. 사용자가 앱에서 수행하는 모든 작업은 Activity에서 발생합니다. Activity는 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공하며, 사용자가 앱에서 수행하는 작업을 나타내는 화면을 제공합니다.",
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ContentsAppBarPreview(){
    CodeLoungeTheme {
        ContentsAppBar(onBackClick = {})
    }
}
@Preview(showBackground = true)
@Composable
fun ContentsPreview(){
    CodeLoungeTheme {
        Contents()
    }
}

@Preview(showBackground = true)
@Composable
fun ListContentsPreview(){
    CodeLoungeTheme {
        ListContents(navController = NavController(LocalContext.current))
    }
}



