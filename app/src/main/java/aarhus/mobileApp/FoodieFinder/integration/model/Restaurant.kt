package aarhus.mobileApp.FoodieFinder.integration.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName("place_id") val id: String?,
    @SerialName("name") val name: String?,
    @SerialName("rating") val rating: Double?,
    @SerialName("user_ratings_total") val ratingsNumber: Int?,
    @SerialName("vicinity") val address: String?
    //price level
    //opening hours
    //photo?

)