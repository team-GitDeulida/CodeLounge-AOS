package codelounge.app.com.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import codelounge.app.com.theme.CustomGreenColor

@Composable
fun ContentsDetails(content: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        val lines = content.lines()
        var insideCodeBlock = false
        var languageTag = ""
        val codeBlockContent = StringBuilder()

        lines.forEachIndexed { index, line ->
            val processedLine = line.replace("\\n", "\n")

            when {
                processedLine.startsWith("```") -> {
                    if (insideCodeBlock) {
                        // 마지막 줄바꿈 제거
                        if (codeBlockContent.isNotEmpty() && codeBlockContent.last() == '\n') {
                            codeBlockContent.setLength(codeBlockContent.length - 1)
                        }
                        if (languageTag.isNotEmpty()) {
                            Text(
                                text = "$languageTag code",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = codeBlockContent.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        insideCodeBlock = false
                        languageTag = ""
                        codeBlockContent.clear()
                    } else {
                        insideCodeBlock = true
                        languageTag = processedLine.removePrefix("```").trim()
                    }
                }

                insideCodeBlock -> {
                    codeBlockContent.append(processedLine).append("\n")
                }

                else -> {
                    val annotatedString = buildAnnotatedString {
                        var endIndex: Int
                        var currentIndex = 0

                        while (currentIndex < processedLine.length) {
                            val startBold = processedLine.indexOf("**", currentIndex)
                            val startHighlight = processedLine.indexOf("##", currentIndex)

                            if (startBold == -1 && startHighlight == -1) {
                                append(processedLine.substring(currentIndex))
                                break
                            }

                            if (startBold != -1 && (startHighlight == -1 || startBold < startHighlight)) {
                            append(processedLine.substring(currentIndex, startBold))
                            endIndex = processedLine.indexOf("**", startBold + 2)
                            if (endIndex == -1) {
                                append(processedLine.substring(startBold))
                                break
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = CustomGreenColor,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                )
                            ) {
                                append(processedLine.substring(startBold + 2, endIndex))
                            }
                                currentIndex = endIndex + 2
                            } else {
                                append(processedLine.substring(currentIndex, startHighlight))
                                endIndex = processedLine.indexOf("##", startHighlight + 2)
                                if (endIndex == -1) {
                                    append(processedLine.substring(startHighlight))
                                    break
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                        color = CustomGreenColor,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(processedLine.substring(startHighlight + 2, endIndex))
                                }
                            currentIndex = endIndex + 2
                        }
                    }
                    }

                    Text(
                        text = annotatedString,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }

            if (index == lines.lastIndex && insideCodeBlock) {
                if (languageTag.isNotEmpty()) {
                    Text(
                        text = "$languageTag code",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Text(
                        text = codeBlockContent.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}