package codelounge.app.com.Model

class LoginRepository {
    fun performGoogleLogin(): Boolean {
        // 실제 로그인 처리 로직 (Firebase 또는 API 호출)
        println("Google 로그인 성공")
        return true
    }

    fun performGithubLogin(): Boolean {
        // 실제 로그인 처리 로직
        println("Github 로그인 성공")
        return true
    }
}