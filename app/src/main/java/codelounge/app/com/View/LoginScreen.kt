package codelounge.app.com.View

import androidx.compose.foundation.Image
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.R
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
        Title()
        Spacer(modifier = Modifier.weight(2f))
        GoogleLoginButton(navController = navController, loginViewModel = loginViewModel)
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Composable
fun Title() {
    Image(
        painter = painterResource(id = R.drawable.code_lounge_banner),
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
}

