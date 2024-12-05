package aarhus.mobileApp.FoodieFinder.integration.firebase.model

import com.google.firebase.firestore.DocumentId

data class RestaurantFB(
    @DocumentId var placeID: String = "",
    var number_of_votes: Int = 0,
    var user_emails: ArrayList<String> = ArrayList(),
    var name: String = ""
)
