package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.zeeumsiraji_10_locationjetpackjob_3.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SeeAllUserScreen(navController: NavHostController) {

    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    var users by remember { mutableStateOf(listOf<User>()) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        db.collection("users")
            .addSnapshotListener { snapshot, _ ->
                users = snapshot?.documents?.map { doc ->
                    User(
                        userId = doc.id,
                        name = doc.getString("name") ?: "",
                        email = doc.getString("email") ?: "",
                        location = doc.getString("location") ?: "",
                        latitude = doc.getDouble("latitude"),
                        longitude = doc.getDouble("longitude")
                    )
                } ?: emptyList()
            }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(users) { user ->

            var name by remember { mutableStateOf(user.name) }
            var email by remember { mutableStateOf(user.email) }
            var location by remember { mutableStateOf(user.location) }
            var latitude by remember { mutableStateOf(user.latitude?.toString() ?: "") }
            var longitude by remember { mutableStateOf(user.longitude?.toString() ?: "") }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(name, { name = it }, label = { Text("Name") })
                    OutlinedTextField(email, { email = it }, label = { Text("Email") })
                    OutlinedTextField(location, { location = it }, label = { Text("Location") })
                    OutlinedTextField(latitude, { latitude = it }, label = { Text("Latitude") })
                    OutlinedTextField(longitude, { longitude = it }, label = { Text("Longitude") })

                    // 📍 AUTO LOCATION BUTTON
                    Button(
                        onClick = {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                getCurrentLocation(
                                    fusedLocationClient,
                                    onSuccess = { lat, lng ->
                                        latitude = lat.toString()
                                        longitude = lng.toString()
                                    },
                                    onError = {
                                        Toast.makeText(
                                            context,
                                            "Unable to get location",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            } else {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                    ) {
                        Text("Auto Get Location")
                    }

                    // ✅ UPDATE BUTTON
                    Button(
                        onClick = {
                            val lat = latitude.toDoubleOrNull()
                            val lng = longitude.toDoubleOrNull()

                            val updateMap = mutableMapOf<String, Any>(
                                "name" to name,
                                "email" to email,
                                "location" to location
                            )

                            lat?.let { updateMap["latitude"] = it }
                            lng?.let { updateMap["longitude"] = it }

                            db.collection("users").document(user.userId)
                                .update(updateMap)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show()
                                }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onSuccess: (Double, Double) -> Unit,
    onError: () -> Unit
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onSuccess(location.latitude, location.longitude)
            } else {
                onError()
            }
        }
        .addOnFailureListener { onError() }
}
