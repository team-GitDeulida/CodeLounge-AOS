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
import codelounge.app.com.ui.theme.BackgroundColor
import codelounge.app.com.ui.theme.WhiteTextColor

@Composable
fun ListContents(navController: NavController, title: String , content: String, appbar: String, parent: String) {
    Scaffold(
        topBar = { ContentsAppBar(onBackClick = { navController.popBackStack() },appbar) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
        Contents(title = title, content = content,parent = parent)
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentsAppBar(onBackClick: () -> Unit, appbar: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = WhiteTextColor,
        ),
        title = {
            Text(appbar)
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}
@Composable
fun Contents(title: String, content: String, parent: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = parent,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ContentsAppBarPreview(){
    CodeLoungeTheme {
        ContentsAppBar(
            onBackClick = {},
            appbar = TODO()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ContentsPreview(){
    CodeLoungeTheme {
        Contents(
            title = TODO(),
            content = TODO(),
            parent = TODO()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListContentsPreview(){
    CodeLoungeTheme {
        ListContents(
            navController = NavController(LocalContext.current),
            title = "Sample Title",
            content = "Sample Content",
            appbar = TODO(),
            parent = TODO()
        )
    }
}



