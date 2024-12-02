package aarhus.mobileApp.FoodieFinder.integration.firebase.model

import com.google.firebase.firestore.DocumentId

data class RestaurantFB(
    @DocumentId var id: String = "",
    var restaurant_id: String = "",
    var number_of_votes: Int = 0,
    var users_id: ArrayList<String> = ArrayList()
)
