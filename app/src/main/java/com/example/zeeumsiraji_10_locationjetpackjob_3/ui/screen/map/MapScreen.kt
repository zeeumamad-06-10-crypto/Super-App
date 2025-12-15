package com.example.myapplicationjetpackjob_3.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.zeeumsiraji_10_locationjetpackjob_3.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MapScreen(
    navController: NavController? = null,
    userId: String? = null,
    showAll: Boolean = false,
    onNavigateSignUp: (() -> Unit)? = null
) {
    val db = FirebaseFirestore.getInstance()
    var singleUser by remember { mutableStateOf<User?>(null) }
    var allUsers by remember { mutableStateOf(listOf<User>()) }

    // Default camera position (Bangladesh)
    val defaultLatLng = LatLng(23.777176, 90.399452)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLatLng, 6f)
    }

    // Load Firestore data
    LaunchedEffect(Unit) {
        if (showAll) {
            db.collection("users")
                .get()
                .addOnSuccessListener { snapshot ->
                    allUsers = snapshot.documents.map { doc ->
                        User(
                            userId = doc.id,
                            name = doc.getString("name") ?: "",
                            email = doc.getString("email") ?: "",
                            latitude = doc.getDouble("latitude"),
                            longitude = doc.getDouble("longitude")
                        )
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(navController?.context, "Failed to load users", Toast.LENGTH_SHORT).show()
                }
        } else if (!userId.isNullOrEmpty()) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { doc ->
                    singleUser = User(
                        userId = doc.id,
                        name = doc.getString("name") ?: "",
                        email = doc.getString("email") ?: "",
                        latitude = doc.getDouble("latitude"),
                        longitude = doc.getDouble("longitude")
                    )
                }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // Single user marker
            singleUser?.let {
                if (it.latitude != null && it.longitude != null) {
                    Marker(
                        state = MarkerState(LatLng(it.latitude, it.longitude)),
                        title = it.name.ifBlank { it.email }
                    )
                }
            }

            // All users markers
            allUsers.forEach { user ->
                if (user.latitude != null && user.longitude != null) {
                    Marker(
                        state = MarkerState(LatLng(user.latitude, user.longitude)),
                        title = user.name.ifBlank { user.email }
                    )
                }
            }
        }
    }
}
