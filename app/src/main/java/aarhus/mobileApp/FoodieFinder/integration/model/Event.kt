package aarhus.mobileApp.FoodieFinder.integration.model

import aarhus.mobileApp.FoodieFinder.integration.firebase.model.UserFB

data class Event(
    val name: String,
    var participants: List<UserFB>,
    var venuesIDs: List<String>
)