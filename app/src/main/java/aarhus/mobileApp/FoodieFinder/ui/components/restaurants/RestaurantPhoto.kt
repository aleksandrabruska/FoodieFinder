package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.BuildConfig
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun RestaurantPhoto(photoReference: String?) {
    if(photoReference != null) {
        Column() {
            val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                    "?photoreference=${photoReference}" +
                    "&maxwidth=400" +
                    "&key=${BuildConfig.GOOGLE_MAPS_API_KEY}"
            SubcomposeAsyncImage(
                model = photoUrl,
                contentDescription = "Place Photo",
                error = {
                    Text("Failed to load image") // Show an error message if the image fails to load
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)

                    .aspectRatio(1f) // Keep a square aspect ratio for the image
            )
        }
    }
}