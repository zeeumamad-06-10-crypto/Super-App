package com.example.zeeumsiraji_10_locationjetpackjob_3.Nav



import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home.BluePrimary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = { }, // empty composable, no title displayed
        navigationIcon = {
            IconButton(onClick = {  scope.launch {
                drawerState.open()
            }
             }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { /* handle notifications */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = BluePrimary
        )
    )
}






