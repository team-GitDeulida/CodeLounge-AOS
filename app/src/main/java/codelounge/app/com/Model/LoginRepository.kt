package codelounge.app.com.Model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class LoginRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    // Firebase Authentication: Google Login
    suspend fun googleSignIn(account: GoogleSignInAccount): String? {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            authResult.user?.uid
        } catch (e: Exception) {
            null
        }
    }

    // Fetch user data from Firebase
    suspend fun fetchUserData(userId: String): Map<String, Any?>? {
        return try {
            val snapshot = database.child("Users").child(userId).get().await()
            if (snapshot.exists()) {
                snapshot.value as Map<String, Any?>
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    // Save user data to Firebase
    suspend fun saveUserData(userId: String, user: Map<String, Any?>): Boolean {
        return try {
            database.child("Users").child(userId).updateChildren(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Save user to database if not exists
    suspend fun saveUserToDatabaseIfNotExists(userId: String, loginPlatform: String) {
        try {
            val snapshot = database.child("Users").child(userId).get().await()
            if (!snapshot.exists()) {
                val user = mapOf(
                    "id" to userId,
                    "loginPlatform" to loginPlatform
                )
                database.child("Users").child(userId).setValue(user).await()
            }
        } catch (e: Exception) {
            // Handle error if necessary
        }
    }
}
