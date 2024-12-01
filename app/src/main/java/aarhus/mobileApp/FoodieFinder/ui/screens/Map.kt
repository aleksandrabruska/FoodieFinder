package aarhus.mobileApp.FoodieFinder.ui.screens

import aarhus.mobileApp.FoodieFinder.ui.components.MapView
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng

private const val PERMISSION = "android.permission.ACCESS_FINE_LOCATION"
@Composable
fun Map(lat: Double, lng: Double) {
    val context = LocalContext.current
    val granted = remember{
        mutableStateOf(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            context,
            PERMISSION
        )
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {isGranted: Boolean ->
        granted.value = isGranted
    }
    LaunchedEffect(key1 = Unit) {
        if(!granted.value){
            launcher.launch("android.permission.ACCESS_FINE_LOCATION")
        }
    }
    MapView(LatLng(lat, lng))
}