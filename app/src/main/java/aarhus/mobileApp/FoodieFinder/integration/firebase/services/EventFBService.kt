package aarhus.mobileApp.FoodieFinder.integration.firebase.services

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.UserFBService.Companion.USERS_COLLECTION_NAME
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
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

    suspend fun saveEvent(ev: EventFB) : String{
        val documentReference = db.collection(EVENTS_COLLECTION_NAME)
            .add(ev)
            .await()
        return documentReference.id

    }

    suspend fun addRestaurantToEvent(ev: EventFB, toAdd: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(ev.id)
            .update("restaurants", FieldValue.arrayUnion(toAdd))
            .await()
    }

    suspend fun addParticipantAlreadyPosted(ev: EventFB, toAdd: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(ev.id)
            .update("participants_already_posted", FieldValue.arrayUnion(toAdd))
            .await()
    }

    suspend fun addParticipantAlreadyVoted(ev: EventFB, toAdd: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(ev.id)
            .update("participants_already_voted", FieldValue.arrayUnion(toAdd))
            .await()
    }

    suspend fun addUserToEvent(ev: EventFB, toAdd: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(ev.id)
            .update("participants", FieldValue.arrayUnion(toAdd))
            .await()
    }

    suspend fun removeUserFromEvent(ev: EventFB, toRemove: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(ev.id)
            .update("participants", FieldValue.arrayRemove(toRemove))
            .await()
    }

    suspend fun getEventByID(id: String) : EventFB? {
        if (!id.isNotBlank()) {
            return null
        }
        var eventDocument = db.collection(EVENTS_COLLECTION_NAME)
            .document(id)
            .get()
            .await()
            .toObject<EventFB>()

        return eventDocument
    }

    suspend fun getUsersEvents(id: String) : List<EventFB>{
        return try {
            val querySnapshot = db.collection(EVENTS_COLLECTION_NAME)
                .whereArrayContains("participants", id)
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                document.toObject<EventFB>()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun removeEvent(eventId: String) {
        db.collection(EVENTS_COLLECTION_NAME)
            .document(eventId)
            .delete()
            .await()
    }
}