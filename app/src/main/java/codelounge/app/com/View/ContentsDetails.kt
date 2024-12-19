package codelounge.app.com.View

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codelounge.app.com.theme.CustomGreenColor

@Composable
fun ContentsDetails(title: String, content: String, parent: String) {
    Log.e("ContentsDetails", "title: $title, content: $content, parent: $parent")
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = parent,
            style = MaterialTheme.typography.bodyLarge.copy(color = CustomGreenColor),
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