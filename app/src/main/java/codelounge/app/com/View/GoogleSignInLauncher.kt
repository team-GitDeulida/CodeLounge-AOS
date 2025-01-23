package codelounge.app.com.View

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import codelounge.app.com.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun GoogleSignInLauncher(
    onGoogleSignIn: (GoogleSignInAccount) -> Unit,
    onError: (String) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.result
            if (account != null) {
                onGoogleSignIn(account)
            }
        } catch (e: Exception) {
            onError(e.message ?: "Google 로그인 실패")
        }
    }

    return {
        // 이전 로그인 상태 초기화
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            (context as? Activity)?.let {
                launcher.launch(signInIntent)
            } ?: onError("Activity 컨텍스트가 필요합니다.")
        }
    }
}

