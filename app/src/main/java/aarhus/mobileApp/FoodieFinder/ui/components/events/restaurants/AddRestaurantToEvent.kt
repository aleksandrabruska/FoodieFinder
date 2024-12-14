package aarhus.mobileApp.FoodieFinder.ui.components.events.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.RestaurantFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.EventFBService
import aarhus.mobileApp.FoodieFinder.integration.firebase.services.RestaurantFBService
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
suspend fun AddRestaurantToEvent(userEntered: UserFB, newRestaurantId: String, eventFound: EventFB, newRestaurantName: String) {
    val eventService = EventFBService()
    val restaurantService = RestaurantFBService()

        try {

            validateAdditionOfRestaurantToEvent(
                userEntered.id,
                newRestaurantId,
                eventFound
            )

            //TODO name
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