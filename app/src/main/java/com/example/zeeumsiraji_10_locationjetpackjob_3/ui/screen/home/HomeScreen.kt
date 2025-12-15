package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home

import DrawerContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zeeumsiraji_10_locationjetpackjob_3.Nav.TopNavBar
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.component.BottomNavBar

val BluePrimary = Color(0xFF007AFF)
val TextGray = Color(0xFF6B7280)

data class Category(val name: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onHomeClick = { navController.navigate("home") },
                onProfileClick = { navController.navigate("sign_in") },
                onSettingsClick = { navController.navigate("sign_up") }
            )
        }
    ) {
        Scaffold(
            bottomBar = { BottomNavBar(drawerState = drawerState, scope = scope, navController = navController) },
            containerColor = Color.White
        )
        { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = innerPadding.calculateBottomPadding())
            ) {
                TopNavBar(drawerState = drawerState, scope = scope)

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = BluePrimary,
                                shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                            )
                            .padding(bottom = 60.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "User",
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Good Morning",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "Jakarta, Indonesia",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Where do you want to go today?",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }

                    SearchBar(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 28.dp)
                            .padding(horizontal = 24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                CategoryGrid(navController = navController) // Pass navController here
            }
        }
    }
}

// --- SearchBar ---
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = Color.White
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Search", color = Color.Gray, fontSize = 16.sp)
        }
    }
}

// --- CategoryGrid ---
@Composable
fun CategoryGrid(navController: NavHostController) {
    val categories = listOf(
        Category("My Shop", Icons.Default.Home),
        Category("Support Team", Icons.Default.Build),
        Category("SeeAllUserScreen", Icons.Default.AddCircle),
        Category("Location", Icons.Default.LocationOn),
        Category("Gas Station", Icons.Default.MoreVert),
        Category("Train", Icons.Default.Send)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            CategoryItem(category = category, navController = navController)
        }
    }
}

// --- CategoryItem ---
@Composable
fun CategoryItem(category: Category, navController: NavHostController) {
    Surface(
        color = Color(0xFFEEEEEE),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .aspectRatio(1f)
            .clickable {
                if (category.name == "My Shop") {
                    navController.navigate("my_shop")
                }
                if (category.name == "SeeAllUserScreen") {
                    navController.navigate("see_all_user")
                }
                if (category.name == "SeeAllUserScreen") {
                    navController.navigate("see_all_user")
                }
                if (category.name == "Location") {
                    navController.navigate("find_location")
                }
                if (category.name == "SeeAllUserScreen") {
                    navController.navigate("see_all_user")
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(category.icon, contentDescription = category.name, tint = BluePrimary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(category.name, fontSize = 14.sp, color = TextGray, fontWeight = FontWeight.Medium)
        }
    }
}
