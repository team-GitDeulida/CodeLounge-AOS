package codelounge.app.com.View

import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.ViewModel.LifeCycleViewModel
import codelounge.app.com.theme.CustomGreenColor

@Composable
fun LifeCycleListScreen(
    navController: NavController,
    viewModel: LifeCycleViewModel = viewModel(),
    firebaseData: Map<String, Any?>,
    searchQuery: String
) {
    val isLoading by viewModel.isLoading.collectAsState(initial = true)

    LaunchedEffect(firebaseData) {
        viewModel.loadData(firebaseData)
    }

    val sections by viewModel.firebaseSections.collectAsState()

    val filteredSections = sections.map { section ->
        section.copy(items = section.items.filter { it.title.contains(searchQuery, ignoreCase = true) })
    }.filter { it.items.isNotEmpty() }

    when {
        isLoading -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(10) {
                    SkeletonSection()
                }
            }
        }

        sections.isEmpty() -> {
                SkeletonSection()

        }

        else -> {
            LazyColumn {
                items(filteredSections) { section ->
                    Text(
                        text = section.sectionTitle,
                        style = MaterialTheme.typography.headlineSmall.copy(color = CustomGreenColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, bottom = 4.dp)
                    )
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(14.dp))
                        .padding(9.dp)
                    ) {
                        section.items.asReversed().forEachIndexed { index, item ->
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable {
                                        val encodedContent = Uri.encode(item.content) // 인코딩
                                        navController.navigate("listContents/${item.title}/$encodedContent")
                                    }
                            )
                            if (index < section.items.size - 1) {
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}
}

@Composable
fun SkeletonSection() {
    Column(modifier = Modifier.padding(8.dp)) { // Section의 전체 레이아웃 조정
        // Section Title Skeleton
        repeat(2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 250.dp, top = 8.dp, bottom = 4.dp)
                    .height(35.dp) // Text 헤드라인에 맞는 높이
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 반복되는 아이템 Skeleton
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(14.dp))
                    .padding(9.dp)
            ) {
            repeat(7) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp) // 텍스트 높이
                        .padding(horizontal = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
}

fun Modifier.shimmerEffect(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    this.background(
        Brush.linearGradient(
            listOf(
                Color.LightGray.copy(alpha = 0.3f),
                Color.LightGray.copy(alpha = alpha),
                Color.LightGray.copy(alpha = 0.3f)
            )
        )
    )
}