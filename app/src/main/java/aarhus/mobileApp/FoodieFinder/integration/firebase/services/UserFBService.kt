package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService.Companion
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService.Companion.RESTAURANTS_COLLECTION_NAME
import androidx.compose.runtime.Composable
import com.google.firebase.firestore.FirebaseFirestore

import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UserFBService {
    val db = FirebaseFirestore.getInstance()

    companion object {
        const val USERS_COLLECTION_NAME = "Users"
    }

    suspend fun getUsers(): List<UserFB> {
        val users = db.collection(UserFBService.USERS_COLLECTION_NAME).get().await()

        return users.documents.mapNotNull { document ->
            document.toObject<UserFB>()
        }
    }

    fun saveUser(user: UserFB) {
        db.collection(USERS_COLLECTION_NAME)
            .document(user.name)
            .set(user)

    }

    fun getUserById(userId: String, callback: (UserFB?) -> Unit) {
        db.collection(USERS_COLLECTION_NAME)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject<UserFB>()
                callback(user)
            }
            .addOnFailureListener { exception ->
                callback(null)
            }
    }

    suspend fun getHorse(id: String): UserFB? {
        return db.collection(USERS_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject<UserFB>()
    }

}