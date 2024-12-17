package codelounge.app.com

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

@Composable
fun FirebaseData(database: DatabaseReference): Map<String, Any?> {
    var allData by remember { mutableStateOf<Map<String, Any?>>(emptyMap()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Firebase에서 데이터 가져오기
    LaunchedEffect(Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 전체 데이터를 Map 형식으로 변환
                allData = snapshot.children.associate { (it.key to it.value) as Pair<String, Any?> }
                // 변환된 데이터를 로그에 표시
                Log.d("FirebaseData", "All Data: $allData")
            }

            override fun onCancelled(error: DatabaseError) {
                errorMessage = "Error: ${error.message}"
                Log.e("FirebaseData", errorMessage ?: "Unknown error")
            }
        })
    }

    return allData
}