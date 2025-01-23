package codelounge.app.com.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.R
import codelounge.app.com.ViewModel.LoginState
import codelounge.app.com.ViewModel.LoginViewModel
import codelounge.app.com.ViewModel.NavigationState

@Composable
fun GoogleLoginButton(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val loginState by loginViewModel.loginState.collectAsState()
    val navigationState by loginViewModel.navigationState.collectAsState()

    val launchGoogleSignIn = GoogleSignInLauncher(
        onGoogleSignIn = { account ->
            loginViewModel.googleLogin(account) {
                userId: String -> loginViewModel.checkUserData(userId) // 사용자 데이터 확인 요청
            }
        },
        onError = { message ->
            // 에러 처리
        }
    )

    // 네비게이션 처리
    LaunchedEffect(navigationState) {
        when (navigationState) {
            is NavigationState.NavigateToSignIn -> {
                navController.navigate("signIn")
                loginViewModel.resetNavigationState()
            }
            is NavigationState.NavigateToHome -> {
                navController.navigate("home")
                loginViewModel.resetNavigationState()
            }
            else -> {}
        }
    }

    // Google 로그인 버튼 UI
    Card(
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable(enabled = loginState !is LoginState.Loading) {
                launchGoogleSignIn()
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier
                    .height(25.dp)
                    .padding(start = 30.dp)
            )
            if (loginState is LoginState.Loading) {
                Text(
                    text = "로그인 중...",
                    modifier = Modifier.padding(start = 30.dp),
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "Google로 로그인",
                    modifier = Modifier.padding(start = 30.dp)
                )
            }
        }
    }
}
