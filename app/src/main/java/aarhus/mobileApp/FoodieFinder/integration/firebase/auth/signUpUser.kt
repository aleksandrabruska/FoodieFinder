package aarhus.mobileApp.FoodieFinder.integration.firebase.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

fun signUpUser(email: String, password: String, callback: (Result<Unit>) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(Result.success(Unit))
            } else {
                callback(Result.failure(task.exception ?: Exception("Unknown error")))
            }
        }

}