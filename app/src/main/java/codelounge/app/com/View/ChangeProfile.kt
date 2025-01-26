package codelounge.app.com.View

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.LoginViewModel
import codelounge.app.com.ViewModel.LoginViewModelFactory
import codelounge.app.com.ViewModel.SignInState
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor
import java.util.Calendar

@Composable
fun ChangeProfileScreen(navController: NavController, loginRepository: LoginRepository) {
    var nickname by remember { mutableStateOf(TextFieldValue("")) }
    var birthdate by remember { mutableStateOf(TextFieldValue("")) }
    var selectedGender by remember { mutableStateOf("") }

    var nicknameError by remember { mutableStateOf(false) }
    var birthdateError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val today = Calendar.getInstance()
    val formattedMonth = String.format("%02d", today.get(Calendar.MONTH) + 1)
    val formattedDay = String.format("%02d", today.get(Calendar.DAY_OF_MONTH))
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedMonth = String.format("%02d", month + 1)
                val formattedDay = String.format("%02d", dayOfMonth)
                birthdate = TextFieldValue("$year.$formattedMonth.$formattedDay")
                birthdateError = false
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
    }

    val loginViewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(loginRepository))
    val signInState by loginViewModel.signInState.collectAsState()

// 상태 변화에 따른 처리
    LaunchedEffect(signInState) {
        when (signInState) {
            is SignInState.Success -> {
                navController.navigate("home") {
                    popUpTo(0) { inclusive = true }
                }
            }

            is SignInState.Error -> {
                // 에러 메시지를 UI에 표시하도록 추가 처리 가능
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    navController.popBackStack()
                }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = WhiteTextColor
                )
            Text(
                    text = "Back",
                style = MaterialTheme.typography.bodyLarge.copy(color = WhiteTextColor),
                    modifier = Modifier.padding(start = 8.dp)
            )
            }
        }
        Text(
            text = "CodeLounge",
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = WhiteTextColor,
                    fontWeight = FontWeight.Bold
                ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "변경 할 정보를 입력해 주세요.",
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    WhiteTextColor,
                    fontWeight = FontWeight.Bold
                ),
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .align(Alignment.Start)
        )
        // 닉네임 입력
        Text(
            text = "닉네임",
            style = MaterialTheme.typography.bodyMedium.copy(WhiteTextColor),
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )
        BasicTextField(
            value = nickname,
            onValueChange = {
                nickname = it
                nicknameError = nickname.text.length < 2 || nickname.text.length > 20
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .border(
                    1.dp,
                    if (nicknameError) Color.Red else Color.White,
                    RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(WhiteTextColor),
            decorationBox = { innerTextField ->
                if (nickname.text.isEmpty()) {
                    Text(
                        text = "2자 이상 20자 이하로 입력해주세요.",
                        color = Color.Gray,
                    )
                }
                innerTextField()
            }
        )
        if (nicknameError) {
            Text(
                text = "닉네임은 2자 이상 20자 이하로 입력해주세요.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // 생년월일 입력
        Text(
            text = "생년월일",
            style = MaterialTheme.typography.bodyMedium.copy(WhiteTextColor),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
                .background(Color.Transparent)
                .border(
                    1.dp,
                    if (birthdateError) Color.Red else Color.White,
                    RoundedCornerShape(10.dp)
                )
                .padding(14.dp)
                .clickable {
                    datePickerDialog.show()
                }
        ) {
            Text(
                text = birthdate.text.ifEmpty { "${today.get(Calendar.YEAR)}.$formattedMonth.$formattedDay" },
                color = WhiteTextColor
            )
        }
        if (birthdateError) {
            Text(
                text = "생년월일을 입력해주세요.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }

        // 성별 선택
        Text(
            text = "성별",
            style = MaterialTheme.typography.bodyMedium.copy(WhiteTextColor),
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenderButton("남자", selectedGender, Modifier.weight(1f)) {
                selectedGender = it
                genderError = false
            }
            GenderButton("여자", selectedGender, Modifier.weight(1f)) {
                selectedGender = it
                genderError = false
            }
            GenderButton("비공개", selectedGender, Modifier.weight(1f)) {
                selectedGender = it
                genderError = false
            }
        }
        if (genderError) {
            Text(
                text = "성별을 선택해주세요.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        // 완료 버튼
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                    .clickable {
                        // 입력값 검증
                        nicknameError = nickname.text.isEmpty() || nickname.text.length !in 2..20
                        birthdateError = birthdate.text.isEmpty()
                        genderError = selectedGender.isEmpty()

                        if (!nicknameError && !birthdateError && !genderError) {
                            loginViewModel.updateUserData(
                                nickname = nickname.text,
                                birthdate = birthdate.text,
                                gender = selectedGender
                            )
                        }
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "완료", style = MaterialTheme.typography.bodyMedium.copy(Color.Black))
            }
        }
    }
}