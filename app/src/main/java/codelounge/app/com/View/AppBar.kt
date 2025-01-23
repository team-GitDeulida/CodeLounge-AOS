package codelounge.app.com.View

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.CustomGreenColor
import codelounge.app.com.theme.WhiteTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(selectedIndex: Int, onSearchQueryChanged: (String) -> Unit) {
    val title = when (selectedIndex) {
        0 -> "CS"
        1 -> "Android"
        2 -> "iOS"
        else -> "CS"
    }

    var isSearchVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    // 너비 애니메이션 처리
    val searchWidth by animateDpAsState(
        targetValue = if (isSearchVisible) 200.dp else 0.dp
    )

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor,
            titleContentColor = WhiteTextColor,
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 제목 표시
                Text(title, modifier = Modifier.weight(1f))

                // 검색창 + 돋보기 아이콘
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 검색창
                    Box(
                        modifier = Modifier
                            .width(searchWidth)
                            .height(40.dp)
                            .background(color = Color.DarkGray, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (isSearchVisible) {
                            BasicTextField(
                                value = text,
                                onValueChange = {
                                    text = it
                                    onSearchQueryChanged(it)
                                },
                                textStyle = TextStyle(color = Color.White),
                                singleLine = true,
                                decorationBox = { innerTextField ->
                                    if (text.isEmpty()) {
                                        Text(
                                            text = "검색하기..",
                                            style = TextStyle(color = Color.Gray)
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = WhiteTextColor,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                isSearchVisible = !isSearchVisible
                                if (!isSearchVisible) {
                                    text = ""
                                    onSearchQueryChanged("")
                                }
                            }
                    )
                }
            }
        }
    )
}