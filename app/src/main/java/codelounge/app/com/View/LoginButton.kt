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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import codelounge.app.com.R

@Composable
fun GoogleLoginButton(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { navController.navigate("home") },
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
            Text(text = "Google로 로그인", modifier = Modifier.padding(start = 30.dp))
        }
    }
}

@Composable
fun GithubLoginButton(navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 50.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { navController.navigate("home") },
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
                    .height(25.dp)
                    .padding(start = 30.dp)
            )
            Text(
                text = "Github로 로그인",
                color = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            )
        }
    }
}