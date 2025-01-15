package codelounge.app.com.viewmodel

import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.filled.School
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroViewModel : ViewModel() {
    val pages = listOf(
        "코드라운지에 오신 것을 환영합니다",
        "컴퓨터 공학기초 (CS)",
        "모바일 개발 (Android & iOS)",
        "코딩 팁과 모범 사례"
    )

    val content = listOf(
        "개발자를 위한 지식 공유와 학습의 공간에 오신 것을 환영합니다",
        "운영체제, 네트워크, 자료구조 등 CS의 핵심 개념을 정리합니다",
        "Android와 iOS 개발에 필요한 필수 지식과 기술을 다룹니다.",
        "효율적인 개발을 위한 유용한 팁과 트릭을 확인하세요."
    )

    val icons = listOf(
        androidx.compose.material.icons.Icons.Filled.Eco,
        androidx.compose.material.icons.Icons.Filled.School,
        androidx.compose.material.icons.Icons.Filled.Android,
        androidx.compose.material.icons.Icons.Filled.PhoneIphone
    )

    var currentPage = mutableStateOf(0)
        private set

    var isMorphing = mutableStateOf(false)
        private set

    fun onNextPage() {
        if (currentPage.value < pages.size - 1) {
            viewModelScope.launch {
                isMorphing.value = true
                delay(600)
                currentPage.value++
                isMorphing.value = false
            }
        }
    }

    fun skipToLastPage() {
        currentPage.value = pages.size - 1
    }
}
