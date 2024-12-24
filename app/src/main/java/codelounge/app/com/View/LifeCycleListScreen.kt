// LifeCycleListScreen.kt
package codelounge.app.com.View

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.ViewModel.LifeCycleViewModel
import codelounge.app.com.theme.CustomGreenColor

@Composable
fun LifeCycleListScreen(
    navController: NavController,
    viewModel: LifeCycleViewModel = viewModel(),
    firebaseData: Map<String, Any?>
) {
    LaunchedEffect(firebaseData) {
        viewModel.loadData(firebaseData)
    }

    Log.d("NavController", "Current route: $firebaseData")
    val sections by viewModel.firebaseSections.collectAsState()

    when {
        sections.isEmpty() -> {
            Text("No data available", modifier = Modifier.padding(16.dp))
        }

        else -> {
            LazyColumn {
                items(sections) { section ->
                    Text(
                        text = section.sectionTitle,
                        style = MaterialTheme.typography.headlineSmall.copy(color = CustomGreenColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        section.items.asReversed().forEach { item ->
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable {
                                        val encodedContent = Uri.encode(item.content) // 인코딩
                                        navController.navigate("listContents/${item.title}/$encodedContent")
                                    }
                            )
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}