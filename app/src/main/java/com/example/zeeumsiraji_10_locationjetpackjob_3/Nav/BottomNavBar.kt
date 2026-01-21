package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.component

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home.BluePrimary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomNavBar(
    navController: NavHostController, // Add this
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            onClick = { },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BluePrimary,
                indicatorColor = Color.White
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountBox, contentDescription = "Note") },
            label = { Text("Note") },
            selected = false,
            onClick = {
                scope.launch {
                    drawerState.open()  // ✅ now this works

                }
                navController.navigate("profile") {
                    // Optional: prevent multiple copies of the same destination
                    launchSingleTop = true
            }},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = false, // you can manage selected state dynamically
            onClick = {
                navController.navigate("settings") {
                    // Optional: prevent multiple copies of the same destination
                    launchSingleTop = true
                }
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray
            )
        )

    }
}

