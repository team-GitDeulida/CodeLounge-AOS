package codelounge.app.com

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import codelounge.app.com.ui.theme.BackgroundColor

@Composable
fun loginActivity(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(BackgroundColor),
        verticalArrangement = Arrangement.SpaceBetween, // 요소 간의 간격을 화면 전체로 분배
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2.5f)) // 상단 여백 채우기

        Text("Code Lounge", fontSize = 50.sp, color = Color.White) // 화면 중간에 유지

        Spacer(modifier = Modifier.weight(3f)) // Text 아래 공간 채우기

        githubLoginButton(navController = navController) // 하단에 배치
        Spacer(modifier = Modifier.height(10.dp))
        googleLoginButton(navController = navController) // 하단에 배치
        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun googleLoginButton(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clickable { navController.navigate("mainActivity") },
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
                    .height(18.dp)
                    .padding(start = 30.dp)
            )
            Text(
                text = "google 계정으로 로그인",
                modifier = Modifier
                    .padding(start = 30.dp)
            )
        }
    }
}

@Composable
fun githubLoginButton(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clickable { navController.navigate("mainActivity") },
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.github_mark),
                contentDescription = "Github Logo",
                modifier = Modifier
                    .height(18.dp)
                    .padding(start = 30.dp)
            )
            Text(
                text = "github 계정으로 로그인",
                color = Color.White,
                modifier = Modifier
                    .padding(start = 30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    loginActivity(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun GoogleLoginButtonPreview() {
    googleLoginButton(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun GithubLoginButtonPreview() {
    githubLoginButton(navController = rememberNavController())
}
