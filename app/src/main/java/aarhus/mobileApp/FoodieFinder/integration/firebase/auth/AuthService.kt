package aarhus.mobileApp.FoodieFinder.integration.firebase.auth

import aarhus.mobileApp.FoodieFinder.domain.Email
import aarhus.mobileApp.FoodieFinder.domain.Password
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await

class AuthService {
    private val auth: FirebaseAuth = Firebase.auth
    private val userService: UserFBService = UserFBService()


    fun checkPassword(password: String, repeat: String) {
        if (password != repeat)
            throw IllegalArgumentException("passwords must match")

    }


    suspend fun singUp(name: String, email: String, password: String, repeat: String) {
        try {
            // check if email is password are correct (syntax)
            val em = Email(email)
            val pass = Password(password)
            checkPassword(password, repeat)

            // save to firebase auth
            val result: FirebaseUser? = auth.createUserWithEmailAndPassword(email, password).await().user

            // save to firestore as a UserFB
            if (result != null) {
                userService.saveUser(UserFB(result.uid, name, email))
            }

        }
        catch (e: Exception) {
            throw e
        }
    }

    suspend fun logIn(email: String, password: String) : UserFB? {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await().user
                ?: return null

            val id = result.uid
            return userService.getUser(id)
        }


        catch (e: Exception) {
            throw e

        }

    }


}