package aarhus.mobileApp.FoodieFinder.integration.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId var id: String = "",
    val name: String = ""
)

