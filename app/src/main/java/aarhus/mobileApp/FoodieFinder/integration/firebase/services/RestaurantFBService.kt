package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService.Companion.USERS_COLLECTION_NAME
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

    suspend fun saveRestaurant(res: RestaurantFB){
        val a = db.collection(RESTAURANTS_COLLECTION_NAME)
            .add(res)
            .await()
    }

    suspend fun searchByPlaceId(placeID: String): RestaurantFB? {
        if (placeID.isBlank()) {
            return null
        }
        val querySnapshot = db.collection(RESTAURANTS_COLLECTION_NAME)
            .whereEqualTo("placeID", placeID)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {
            null
        } else {
            querySnapshot.documents.firstOrNull()?.toObject<RestaurantFB>()
        }
    }


}