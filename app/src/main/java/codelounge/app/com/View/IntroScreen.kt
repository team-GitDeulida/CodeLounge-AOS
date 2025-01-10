package codelounge.app.com.View


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor
import kotlinx.coroutines.launch
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Introscreen(navController: NavController) {
    val pages = listOf(
        "코드라운지에 오신 것을 환영합니다",
        "컴퓨터 공학기초 (CS)",
        "모바일 개발 (Android & iOS)",
        "코딩 팁과 모범 사례"
    )

    val content = listOf(
        "개발자를 위한 지식 공유와 학습의 공간에 오신 것을 환영합니다",
        "운영체제, 네트워크, 자료구조 등 CS의 핵심 개념을 정리합니다",
        "Android와 iOS 개발에 필요한 필수 지식과 기술을 다룹니다.",
        "효율적인 개발을 위한 유용한 팁과 트릭을 확인하세요."
    )

    val icons = listOf(
        Icons.Filled.Eco,
        Icons.Filled.School,
        Icons.Filled.Android,
        Icons.Filled.PhoneIphone
    )

    var currentPage by rememberSaveable { mutableStateOf(0) }
    var isMorphing by remember { mutableStateOf(false) }
    val iconIndex = currentPage % icons.size

    val coroutineScope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = if (isMorphing) 0f else 1f,
        animationSpec = tween(600, easing = FastOutSlowInEasing)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isMorphing) 0f else 1f,
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(150.dp)
            ) {
                Crossfade(targetState = icons[iconIndex]) { icon ->
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                alpha = alpha
                            ),
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 슬라이딩 애니메이션이 적용된 제목
            AnimatedContent(
                targetState = pages[currentPage],
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
                }
            ) { pageText ->
                Text(
                    text = pageText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = WhiteTextColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 슬라이딩 애니메이션이 적용된 설명 텍스트
            AnimatedContent(
                targetState = content[currentPage],
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
                }
            ) { contentText ->
                Text(
                    text = contentText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }

        Button(
            onClick = {
                if (currentPage < pages.size - 1) {
                    coroutineScope.launch {
                        isMorphing = true // 모핑 시작
                        delay(600)
                        currentPage++
                        isMorphing = false // 모핑 종료
                    }
                } else {
                    navController.navigate("login")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .fillMaxWidth(0.5f)
        ) {
            Text(text = if (currentPage < pages.size - 1) "Continue" else "Login into PS App", color = Color.Black)
        }
    }
}




