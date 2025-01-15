package codelounge.app.com.View

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor
import kotlinx.coroutines.delay
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import codelounge.app.com.viewmodel.IntroViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Introscreen(navController: NavController, introViewModel: IntroViewModel = viewModel()) {
    val pages = introViewModel.pages
    val content = introViewModel.content
    val icons = introViewModel.icons

    val currentPage = introViewModel.currentPage.value
    val isMorphing = introViewModel.isMorphing.value

    val scale by animateFloatAsState(
        targetValue = if (isMorphing) 0f else 1f,
        animationSpec = tween(600)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isMorphing) 0f else 1f,
        animationSpec = tween(600)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(5.dp)
    ) {
        if (currentPage < pages.size - 1) {
            Text(
                text = "Skip",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { introViewModel.skipToLastPage() }
            )
        }

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
                Crossfade(targetState = icons[currentPage % icons.size]) { icon ->
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
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            pages.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (index == currentPage) 12.dp else 8.dp)
                        .background(
                            color = if (index == currentPage) Color.White else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }

        Button(
            onClick = {
                if (currentPage < pages.size - 1) {
                    introViewModel.onNextPage()
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
            Text(
                text = if (currentPage < pages.size - 1) "Continue" else "Login into PS App",
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
