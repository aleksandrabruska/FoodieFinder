package aarhus.mobileApp.FoodieFinder.integration.firebase

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.toObject


class UserService {
    val db = FirebaseFirestore.getInstance()
    companion object {
        const val USER_COLLECTION_NAME = "users"
    }

    suspend fun getUsers(): List<User> {
        val users = db.collection(USER_COLLECTION_NAME).get().await()


        return users.documents.mapNotNull { document ->
            document.toObject<User>()
        }
    }

    suspend fun getUser(id: String): User? {
        return db.collection(USER_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject<User>()
    }
}