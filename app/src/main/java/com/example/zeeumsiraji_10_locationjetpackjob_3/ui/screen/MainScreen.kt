import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.myapplicationjetpackjob_3.ui.screen.MapScreen
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.SettingsScreen
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.auth.SignInScreen
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.auth.SignUpScreen
import kotlinx.coroutines.launch
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home.HomeScreen

import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.shop.MyShopScreen
import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home.SeeAllUserScreen

import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.SingleProfileScreen


@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onHomeClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("home") { popUpTo("home") { inclusive = true } }
                    },
                    onProfileClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("sign_in")
                    },
                    onSettingsClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("sign_up")
                    }
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "home") {

            composable("home") { HomeScreen(navController) }
            composable("sign_in") { SignInScreen(navController) }
            composable("sign_up") { SignUpScreen(navController) }
            composable("my_shop") { MyShopScreen(navController) }
            composable("see_all_user") { SeeAllUserScreen(navController) }
            composable("find_location") { MapScreen(navController = navController, userId = null, showAll = true, onNavigateSignUp = null) }
            composable("settings") { SettingsScreen(navController) }

            // List all profiles
            composable("profile") { ProfileScreen(navController) }

            // Add profile (no ID)
            composable("single_profile_screen") { SingleProfileScreen(navController, profileId = null) }

            // Edit profile (with ID)
            composable(
                route = "single_profile_screen/{profileId}",
                arguments = listOf(navArgument("profileId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("profileId")
                SingleProfileScreen(navController, profileId = id)
            }
        }



    }
}
