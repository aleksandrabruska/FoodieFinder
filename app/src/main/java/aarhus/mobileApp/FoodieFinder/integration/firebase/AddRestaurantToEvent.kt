package aarhus.mobileApp.FoodieFinder.integration.firebase

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import android.annotation.SuppressLint
import android.util.Log


@SuppressLint("SuspiciousIndentation")
suspend fun AddRestaurantToEvent(userEntered: UserFB, newRestaurantId: String,
                                 eventFound: EventFB, newRestaurantName: String) {
    val eventService = EventFBService()
    val restaurantService = RestaurantFBService()

        try {

            validateAdditionOfRestaurantToEvent(
                userEntered.id,
                newRestaurantId,
                eventFound
            )

            val resToAdd = RestaurantFB(
                "",
                newRestaurantId,
                eventFound.id,
                0,
                newRestaurantName
            )

            eventService.addRestaurantToEvent(eventFound, newRestaurantId)
            eventService.addParticipantAlreadyPosted(
                eventFound,
                userEntered.id
            )
            restaurantService.saveRestaurant(resToAdd)

       } catch (e: Exception) {
            Log.v("ADDING RESTAURANT", e.message.toString())
            throw Exception(e.message)
        }

}