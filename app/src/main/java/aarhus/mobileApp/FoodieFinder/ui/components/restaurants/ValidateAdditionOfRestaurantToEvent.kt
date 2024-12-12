package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.EventFB

fun validateAdditionOfRestaurantToEvent(userAddingID: String, placeID: String, event: EventFB) {
    if (userAddingID in event.participants_already_posted) {
        throw IllegalArgumentException("You have already posted")
    }
    if(placeID in event.restaurants) {
        throw IllegalArgumentException("Restaurant is already in the list")
    }

}