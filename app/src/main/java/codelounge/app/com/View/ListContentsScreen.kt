// ListContentsScreen.kt
package codelounge.app.com.View

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import codelounge.app.com.ViewModel.ListContentsViewModel

@Composable
fun ListContentsScreen(
    viewModel: ListContentsViewModel = viewModel(),
    title: String,
    content: String,
) {
    viewModel.setContentItem(title, content)
    val contentItem by viewModel.contentItem.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = innerPadding.calculateTopPadding(), end = innerPadding.calculateEndPadding(layoutDirection = LayoutDirection.Ltr), bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
        ) {
            contentItem?.let {
                ContentsDetails(
                    content = it.content,
                )
            }
        }
    }
}