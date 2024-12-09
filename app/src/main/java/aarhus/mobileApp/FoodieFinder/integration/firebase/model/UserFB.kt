package aarhus.mobileApp.FoodieFinder.integration.firebase.model


import com.google.firebase.firestore.DocumentId

data class UserFB(
    @DocumentId var id: String = "",
    var name: String = "",
    val email: String = "",
    val friends: ArrayList<String> = ArrayList(),
    val events: ArrayList<String> = ArrayList()
)
