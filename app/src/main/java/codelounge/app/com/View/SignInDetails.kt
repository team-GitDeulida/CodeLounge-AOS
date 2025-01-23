package codelounge.app.com.View

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import codelounge.app.com.Model.LoginRepository
import codelounge.app.com.ViewModel.LoginViewModel
import codelounge.app.com.ViewModel.LoginViewModelFactory
import codelounge.app.com.ViewModel.SignInState
import codelounge.app.com.theme.BackgroundColor
import codelounge.app.com.theme.WhiteTextColor
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun SignInDetails(navController: NavController, loginRepository: LoginRepository) {
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
                navController.navigate("home")
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
        Text(
            text = "CodeLounge",
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = WhiteTextColor,
                    fontWeight = FontWeight.Bold
                ),
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = "회원가입에 필요한 정보를 입력해주세요.",
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
                            loginViewModel.saveUserData(
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


@Composable
fun GenderButton(gender: String, selectedGender: String, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    val isSelected = gender == selectedGender
    val backgroundColor = if (isSelected) Color.White else Color.Transparent
    val textColor = if (isSelected) Color.Black else WhiteTextColor

    Box(
        modifier = modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(8.dp) // 모서리 둥글게
            )
            .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp)) // 테두리 흰색 추가
            .clickable { onClick(gender) }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = gender,
            color = textColor,
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
    }
}
