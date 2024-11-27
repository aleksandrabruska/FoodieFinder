package aarhus.mobileApp.FoodieFinder.integration.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
    @SerialName("place_id") val id: String?,
    @SerialName("name") val name: String?,
    @SerialName("rating") val rating: Double?,
    @SerialName("user_ratings_total") val ratingsNumber: Int?,
    @SerialName("vicinity") val address: String?,
    @SerialName("price_level") val price_level: Int?,
    @SerialName("website") val website: String?,
    var photoReference: String? = null,
    var openingHours: Array<String>? = null
    //price level
    //opening hours
    //photo?

)