package aarhus.mobileApp.FoodieFinder.integration.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    @SerialName("author_name") val author: String,
    @SerialName("text") val text: String
)