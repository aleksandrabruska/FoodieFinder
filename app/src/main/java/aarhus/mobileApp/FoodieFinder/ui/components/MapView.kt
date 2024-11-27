package aarhus.mobileApp.FoodieFinder.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline

@Composable
fun MapView(){
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(21.0, 32.0), 15f
            //locations[0].let { LatLng(it.latitude, it.longitude) }, 15f
        )
    }

    GoogleMap(cameraPositionState=cameraPositionState) {
       // Polyline(
            //color = Color.Magenta,
            //pattern = listOf(Dash(2f)),
            //points = locations.map { LatLng(it.latitude, it.longitude) }
        //)
    }
}