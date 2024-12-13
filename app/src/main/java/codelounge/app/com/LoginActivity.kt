package codelounge.app.com

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun loginActivity(navController: NavController) {
    Column {
    Text("Code Lounge")
        Button(onClick = { navController.navigate("home") }) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    loginActivity(navController = rememberNavController())
}