package aarhus.mobileApp.FoodieFinder.integration.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun logInUser(email: String, password: String, callback: (Result<FirebaseUser?>) -> Unit) {
    val auth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                callback(Result.success(user))
            } else {
                callback(Result.failure(task.exception ?: Exception("Unknown error")))
            }
        }

}