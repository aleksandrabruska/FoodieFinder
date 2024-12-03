package aarhus.mobileApp.FoodieFinder.integration.firebase.model


import com.google.firebase.firestore.DocumentId

data class UserFB(
    @DocumentId var name: String = "",
    val email: String = "",
    val friends: ArrayList<String> = ArrayList()
)
