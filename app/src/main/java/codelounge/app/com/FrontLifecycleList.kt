package codelounge.app.com

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FrontLifecycleList() {
    println("Front Lifecycle List")
}

@Preview(showBackground = true)
@Composable
fun FrontLifecycleListPreview() {
    FrontLifecycleList()
}