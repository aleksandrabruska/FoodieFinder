package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
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
            .document(user.id)
            .set(user)

    }

    suspend fun getUser(id: String): UserFB? {

        if (!id.isNotBlank()) {
            return null
        }
        var userDocument = db.collection(USERS_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject<UserFB>()

        return userDocument
    }

    suspend fun addFriend(userId: String, toAdd: String) {
        db.collection(USERS_COLLECTION_NAME)
            .document(userId)
            .update("friends", FieldValue.arrayUnion(toAdd))
            .await()
    }

    suspend fun removeFriend(userId: String, toRemove: String) {
        db.collection(USERS_COLLECTION_NAME)
            .document(userId)
            .update("friends", FieldValue.arrayRemove(toRemove))
            .await()
    }



}