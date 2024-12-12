package aarhus.mobileApp.FoodieFinder.integration.firebase.model

import com.google.firebase.firestore.DocumentId

data class RestaurantFB(
    @DocumentId var id: String = "",
    var placeID: String = "",
    var eventID: String = "", //for deletion
    var number_of_votes: Int = 0,
    var name: String = "",
    var user_ids: ArrayList<String> = ArrayList()
    )
