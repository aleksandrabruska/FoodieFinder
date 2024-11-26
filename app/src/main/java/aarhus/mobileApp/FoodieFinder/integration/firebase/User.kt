package aarhus.mobileApp.FoodieFinder.integration.firebase

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId var id: String = "",
    val name: String = ""
)

