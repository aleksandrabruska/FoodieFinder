package aarhus.mobileApp.FoodieFinder.ui.components.restaurants

import aarhus.mobileApp.FoodieFinder.BuildConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun RestaurantPhoto(photoReference: String?) {
    if(photoReference != null) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo" +
                        "?photoreference=${photoReference}" +
                        "&maxwidth=400" +
                        "&key=${BuildConfig.GOOGLE_MAPS_API_KEY}"
                SubcomposeAsyncImage(
                    model = photoUrl,
                    contentDescription = "Place Photo",
                    error = {
                        Text("Failed to load image")
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                        //.width(100.dp)
                        .aspectRatio(1f)
                        //.width(100.dp)
                )
            }
        }
    }
}