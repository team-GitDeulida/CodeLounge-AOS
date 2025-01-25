package codelounge.app.com.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.LoginViewModel
import codelounge.app.com.ViewModel.LoginViewModelFactory
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileScreen(navController: NavController) {
    val loginRepository = LoginRepository()
    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(loginRepository))
    val user by loginViewModel.user.collectAsState()

    // Fetch the current user data
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    LaunchedEffect(currentUserId) {
        if (currentUserId != null) {
            loginViewModel.fetchUserData(currentUserId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Page",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
        )

        // User Info Section
        user?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundColor)
                    .border(2.dp, Color.Gray, shape = RoundedCornerShape(14.dp)) // 테두리 추가 및 라운드 처리
                    .padding(14.dp)
                    .clickable(onClick = {
                        // Navigate to Edit Profile
                    })
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = it.nickname ?: "N/A",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = WhiteTextColor
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Text(
                    text = "CodeLounge ${
                        it.registerDate?.let { registerDate ->
                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                            val registerDateParsed = sdf.parse(registerDate)
                            val diff =
                                (Date().time - registerDateParsed.time) / (1000 * 60 * 60 * 24)
                            diff.toString()
                        } ?: "0"
                    }일 째",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = WhiteTextColor
                    ))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Setting Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(14.dp))
                .padding(10.dp)
        ) {
            SettingItem("공지사항") {
                // Navigate to Notice
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem("문의하기") {
                // Navigate to Inquiry
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(14.dp))
                .padding(9.dp)
        ) {
            SettingItem("개인정보처리방침") {
                // Navigate to Privacy Policy
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SettingItem("버전정보") {
                // Navigate to Version Info
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(14.dp))
                .padding(9.dp)
        ) {
            SettingItem("계정탈퇴") {
                val currentUser = FirebaseAuth.getInstance().currentUser
                currentUser?.let { user ->
                    val userId = user.uid
                    user.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val database = LoginRepository().database
                            database.child("Users").child(userId).removeValue()
                                .addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        navController.navigate("login") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    } else {
                                        // Handle error if necessary
                                    }
                                }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Logout Button
        Text(
            text = "로그아웃",
            color = Color.Red,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
        )
    }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 9.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}