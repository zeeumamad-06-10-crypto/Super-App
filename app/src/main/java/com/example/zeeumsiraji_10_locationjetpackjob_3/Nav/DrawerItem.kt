import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color


// --- Drawer Content ---
@Composable
fun DrawerContent(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit


) {
    Column(modifier = Modifier.fillMaxHeight().padding(end = 100.dp).background(Color.White)) {
        DrawerItem(icon = { Icon(Icons.Default.Home, contentDescription = "Home") }, label = "Home") {
            onHomeClick()
        }
        DrawerItem(icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }, label = "Sign In") {
            onProfileClick()
        }
        DrawerItem(icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }, label = "Sign Up") {
            onSettingsClick()
        }
    }
}

@Composable
fun DrawerItem(icon: @Composable () -> Unit, label: String, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 18.sp)
    }
}


