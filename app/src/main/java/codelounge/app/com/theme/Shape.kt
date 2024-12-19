package codelounge.app.com.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),    // 작은 컴포넌트의 모서리
    medium = RoundedCornerShape(8.dp),  // 중간 크기 컴포넌트의 모서리
    large = RoundedCornerShape(16.dp)   // 큰 컴포넌트의 모서리
)