package codelounge.app.com.Model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepository(private val database: DatabaseReference) {

    fun fetchFirebaseData(): Flow<Map<String, Any?>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.child("Posts").children.associate { it.key!! to it.value }
                trySend(data).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                close(Exception("Error: ${error.message}"))
            }
        }
        database.addListenerForSingleValueEvent(listener)

        awaitClose {
            database.removeEventListener(listener)
        }
    }
}