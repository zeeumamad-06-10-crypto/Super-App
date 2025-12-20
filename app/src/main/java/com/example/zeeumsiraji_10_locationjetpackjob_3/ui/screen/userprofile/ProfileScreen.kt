
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database.UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = viewModel()
) {
    val profiles by viewModel.allProfiles.observeAsState(emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("User Profiles") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("single_profile_screen")
            }) {
                Text("+")
            }
        }
    ) { padding ->
        if (profiles.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Profiles Found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(profiles) { profile ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("single_profile_screen/${profile.id}")
                            },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Name: ${profile.name}", style = MaterialTheme.typography.titleMedium)
                            Text("Email: ${profile.email}")
                            Text("District: ${profile.district}")
                        }
                    }
                }
            }
        }
    }
}



