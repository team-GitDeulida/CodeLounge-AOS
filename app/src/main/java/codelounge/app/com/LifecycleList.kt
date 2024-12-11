package codelounge.app.com

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import codelounge.app.com.ui.theme.CodeLoungeTheme

@Composable
fun LifecycleList(modifier: Modifier = Modifier) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val numbers = remember { (1..100).map { it * it }.toList() }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycleState.value = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val filteredNumbers = when (lifecycleState.value) {
        Lifecycle.Event.ON_RESUME -> numbers.filter { it % 2 == 0 }
        Lifecycle.Event.ON_PAUSE -> numbers.filter { it % 2 != 0 }
        else -> numbers
    }

    LazyColumn {
        items(filteredNumbers) { number ->
            Card(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "Number: $number",
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
        LifecycleList()
    }
}
