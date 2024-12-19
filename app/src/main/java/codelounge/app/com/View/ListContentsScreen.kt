package codelounge.app.com.View

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import codelounge.app.com.ViewModel.ListContentsViewModel

@Composable
fun ListContentsScreen(
    navController: NavController,
    viewModel: ListContentsViewModel = viewModel(),
    appbar: String,
    title: String,
    content: String,
    parent: String
) {
    viewModel.setContentItem(title, content, parent)
    val contentItem by viewModel.contentItem.collectAsState()

    Scaffold(
        topBar = {
            ContentsAppBar(
                navController = navController,
                appbar = appbar
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            contentItem?.let {
                ContentsDetails(
                    title = it.title,
                    content = it.content,
                    parent = it.parent
                )
            }
        }
    }
}
