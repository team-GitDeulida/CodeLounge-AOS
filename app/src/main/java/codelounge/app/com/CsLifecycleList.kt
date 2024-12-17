package codelounge.app.com

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CsLifecycleList() {
    println("Cs Lifecycle List")
}

@Preview(showBackground = true)
@Composable
fun CsLifecycleListPreview() {
    CsLifecycleList()
}