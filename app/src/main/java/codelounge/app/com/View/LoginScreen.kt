package codelounge.app.com.View

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.LoginViewModel
import codelounge.app.com.ViewModel.LoginViewModelFactory
import codelounge.app.com.theme.BackgroundColor


@Composable
fun LoginScreen(navController: NavController, repository: LoginRepository) {
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2.5f))

        Text("Code Lounge", fontSize = 50.sp, color = Color.White)
        Spacer(modifier = Modifier.weight(3f))
        GithubLoginButton(navController = navController)
        Spacer(modifier = Modifier.height(10.dp))
        GoogleLoginButton(navController = navController)
        Spacer(modifier = Modifier.weight(1f))
    }

    val loginStatus by loginViewModel.loginStatus.collectAsState()
    if (loginStatus.isNotEmpty()) {
        println(loginStatus) // 디버그 출력 (필요시 Dialog나 Toast로 변경 가능)
    }
}
