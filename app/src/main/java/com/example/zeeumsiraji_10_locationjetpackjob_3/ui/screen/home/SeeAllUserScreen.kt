package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun SeeAllUserScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // 🔹 Collect StateFlow from ViewModel
    val users by userViewModel.users.collectAsState()
    val searchQuery by userViewModel.searchQuery.collectAsState() // FIX: val instead of var

    // 📍 Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔹 Search Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { userViewModel.setSearchQuery(it) }, // update via ViewModel
                label = { Text("Search by Name, Email, Location") },
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = { /* just triggers recomposition */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) { Text("Search") }
        }

        // 🔹 Filter users
        val filteredUsers = users.filter { user ->
            val query = searchQuery.lowercase()
            user.name.lowercase().contains(query) ||
                    user.email.lowercase().contains(query) ||
                    user.location.lowercase().contains(query)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredUsers) { user ->

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

                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = latitude,
                            onValueChange = { latitude = it },
                            label = { Text("Latitude") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = longitude,
                            onValueChange = { longitude = it },
                            label = { Text("Longitude") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 🔹 Buttons Row (scrollable)
                        val scrollState = rememberScrollState()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(scrollState),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            // 📍 Auto Location
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
                                                Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()
                                            }
                                        )
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                            ) { Text("Auto Location") }

                            // ✅ Update
                            Button(
                                onClick = {
                                    userViewModel.updateUser(
                                        user,
                                        name,
                                        email,
                                        location,
                                        latitude.toDoubleOrNull(),
                                        longitude.toDoubleOrNull()
                                    ) { success ->
                                        Toast.makeText(
                                            context,
                                            if (success) "Updated" else "Update Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                            ) { Text("Update") }

                            // ❌ Delete
                            Button(
                                onClick = {
                                    userViewModel.deleteUser(user) { success ->
                                        Toast.makeText(
                                            context,
                                            if (success) "Deleted" else "Delete Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) { Text("Delete") }

                        } // End Row
                    } // End Column
                } // End Card
            } // End items
        } // End LazyColumn
    } // End Column
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onSuccess: (Double, Double) -> Unit,
    onError: () -> Unit
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) onSuccess(location.latitude, location.longitude)
            else onError()
        }
        .addOnFailureListener { onError() }
}
