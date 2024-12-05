package aarhus.mobileApp.FoodieFinder.integration.firebase.model

import com.google.firebase.firestore.DocumentId

data class EventFB(
    @DocumentId var id: String = "",
    val name: String =  "",
    val date: String = "",
    val participants: ArrayList<String> = ArrayList(),
    val participants_already_posted: ArrayList<String> = ArrayList(),
    val participants_already_voted: ArrayList<String> = ArrayList(),
    val restaurants: ArrayList<String> = ArrayList(),

    )
