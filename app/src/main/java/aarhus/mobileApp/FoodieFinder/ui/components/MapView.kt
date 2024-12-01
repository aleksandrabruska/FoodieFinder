package aarhus.mobileApp.FoodieFinder.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
fun MapView(latLng: LatLng){
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            latLng, 15f
            //locations[0].let { LatLng(it.latitude, it.longitude) }, 15f
        )
    }

    GoogleMap(cameraPositionState=cameraPositionState) {
        Marker(
            state = MarkerState(
                position = latLng),
            title = "restaurant location",
            snippet = "Localized"
        )
    }
}