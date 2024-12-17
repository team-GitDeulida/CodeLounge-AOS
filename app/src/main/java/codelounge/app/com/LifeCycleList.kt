package codelounge.app.com

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.ui.theme.CodeLoungeTheme
import codelounge.app.com.ui.theme.CustomGreenColor

@Composable
fun LifeCycleList(navController: NavController, firebaseData: Map<String, Any?>) {
    val titles = remember { firebaseData.keys.toList() }
    LazyColumn {
        items(titles) { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(color = CustomGreenColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
            val nestedData = firebaseData[title] as? Map<String, Map<String, Any?>>
                    nestedData?.let {
                        Column(modifier = Modifier.padding(8.dp)) {
                            it.values.forEach { item ->
                                Text(
                                    text = item["title"] as? String ?: "",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .clickable { navController.navigate("listContents") }
                                )
                                HorizontalDivider()
                            }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ListPreview() {
    CodeLoungeTheme {
        LifeCycleList(
            firebaseData = mapOf("Algorithm1" to "Description1", "Algorithm2" to "Description2"),
            navController = TODO()
        )
    }
}

