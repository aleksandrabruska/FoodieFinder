package aarhus.mobileApp.FoodieFinder.integration.model

data class Event(
    val name: String,
    var participants: List<User>,
    var venuesIDs: List<String>
)