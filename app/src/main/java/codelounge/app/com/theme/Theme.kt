package codelounge.app.com.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext

// 기본 색상 (Android 11 이하)
private val LightColors = lightColorScheme(
    primary = Purple500,
    onPrimary = White,
    background = White,
    onBackground = Black
)

private val DarkColors = darkColorScheme(
    primary = LineBackgroundColor,
    onPrimary = CustomGreenColor,
    background = BackgroundColor,
    onBackground = WhiteTextColor,
)

@Composable
        fun CodeLoungeTheme(
            useDynamicColors: Boolean = false,
            darkTheme: Boolean = true,
            content: @Composable () -> Unit
        ) {
            val colors = when {
                // Android 12(API 31) 이상에서 동적 테마 활성화
                useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                }
                darkTheme -> DarkColors
                else -> LightColors
            }

            MaterialTheme(
                colorScheme = colors,
                typography = Typography,
                shapes = Shapes,
                content = content
            )
        }