package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService.Companion.USERS_COLLECTION_NAME
import com.google.firebase.firestore.FieldValue
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
        db.collection(RESTAURANTS_COLLECTION_NAME)
            .add(res)
            .await()
    }

    suspend fun getRestaurantByID(id: String): RestaurantFB? {
        if (!id.isNotBlank()) {
            return null
        }
        var restaurantDocument = db.collection(RESTAURANTS_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject<RestaurantFB>()

        return restaurantDocument
    }

    suspend fun searchByPlaceAndEventId(placeID: String, eventID: String): RestaurantFB? {
        if (placeID.isBlank()) {
            return null
        }
        val querySnapshot = db.collection(RESTAURANTS_COLLECTION_NAME)
            .whereEqualTo("placeID", placeID)
            .whereEqualTo("eventID", eventID)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {
            null
        } else {
            querySnapshot.documents.firstOrNull()?.toObject<RestaurantFB>()
        }
    }

    suspend fun addVote(res: RestaurantFB) {
        db.collection(RESTAURANTS_COLLECTION_NAME)
            .document(res.id)
            .update("number_of_votes", FieldValue.increment(1))
    }


}