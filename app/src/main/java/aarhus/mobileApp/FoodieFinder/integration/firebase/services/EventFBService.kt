package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class EventFBService {
    val db = FirebaseFirestore.getInstance()

    companion object {
        const val EVENTS_COLLECTION_NAME = "Events"
    }

    suspend fun getEvents(): List<EventFB> {
        val users = db.collection(EVENTS_COLLECTION_NAME).get().await()


        return users.documents.mapNotNull { document ->
            document.toObject<EventFB>()
        }
    }

    fun saveEvent(ev: EventFB) {
        db.collection(EVENTS_COLLECTION_NAME)
            .add(ev)


    }
}