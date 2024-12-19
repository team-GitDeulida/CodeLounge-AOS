package codelounge.app.com.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import codelounge.app.com.theme.CustomGreenColor

@Composable
fun ContentsDetails(content: String) {
    val annotatedText = buildAnnotatedString {
        var startIndex = 0
        while (startIndex < content.length) {
            val startTag = content.indexOf("**", startIndex)
            if (startTag == -1) {
                // 태그가 더 이상 없으면 나머지 텍스트 추가
                append(content.substring(startIndex))
                break
            }
            val endTag = content.indexOf("**", startTag + 2)
            if (endTag == -1) {
                // 닫는 태그가 없으면 나머지 텍스트 추가
                append(content.substring(startIndex))
                break
            }
            // 태그 앞의 텍스트 추가
            append(content.substring(startIndex, startTag))
            // 태그 안의 텍스트에 스타일 적용
            withStyle(style = SpanStyle(color = CustomGreenColor) + MaterialTheme.typography.headlineSmall.toSpanStyle()) {
                append(content.substring(startTag + 2, endTag))
            }
            // 다음 인덱스로 이동
            startIndex = endTag + 2
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
        Text(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium
        )
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
    }
}