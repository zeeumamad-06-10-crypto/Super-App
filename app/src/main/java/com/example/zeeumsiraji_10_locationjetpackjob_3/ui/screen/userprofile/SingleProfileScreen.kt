package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database.UserProfile
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database.UserProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun SingleProfileScreen(
    navController: NavController,
    profileId: Int? = null,
    viewModel: UserProfileViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    // Observe profile if editing
    val profile by profileId?.let { viewModel.getProfileById(it).observeAsState() } ?: mutableStateOf(null)

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            email = it.email
            district = it.district
            dateOfBirth = it.dateOfBirth
            mobile = it.mobile
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text("District") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { dateOfBirth = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = mobile,
            onValueChange = { mobile = it },
            label = { Text("Mobile") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    val newProfile = UserProfile(
                        id = profile?.id ?: 0,
                        name = name,
                        email = email,
                        district = district,
                        dateOfBirth = dateOfBirth,
                        mobile = mobile
                    )
                    scope.launch {
                        if (profile == null) viewModel.insert(newProfile)
                        else viewModel.update(newProfile)
                        navController.popBackStack()
                    }
                }
            ) {
                Text(if (profile == null) "Add" else "Update")
            }

            profile?.let {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = {
                        scope.launch {
                            viewModel.delete(it)
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
