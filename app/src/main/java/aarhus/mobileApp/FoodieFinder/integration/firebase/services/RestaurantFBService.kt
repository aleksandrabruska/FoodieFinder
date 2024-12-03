package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class RestaurantFBService {
    val db = FirebaseFirestore.getInstance()

    companion object {
        const val RESTAURANTS_COLLECTION_NAME = "Restaurants"
    }

    suspend fun getRestaurants(): List<RestaurantFB> {
        val users = db.collection(RESTAURANTS_COLLECTION_NAME).get().await()


        return users.documents.mapNotNull { document ->
            document.toObject<RestaurantFB>()
        }
    }

    fun saveRestaurant(res: RestaurantFB) {
        db.collection(RESTAURANTS_COLLECTION_NAME).add(res)
    }
}