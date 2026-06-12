package onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.model.AppDatabase
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.model.User
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.ui.LoginScreen
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.ui.MainScreen
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.ui.RegisterScreen
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.ui.theme.Onurcan_Tras_Freelancer_Finans_ve_Portfoy_yönetimiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()

        enableEdgeToEdge()
        setContent {
            val systemTheme = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemTheme) }
            var loggedInUser by remember { mutableStateOf<User?>(null) }
            var currentScreen by remember { mutableStateOf("login") } // "login" or "register"

            Onurcan_Tras_Freelancer_Finans_ve_Portfoy_yönetimiTheme(darkTheme = isDarkMode) {
                if (loggedInUser == null) {
                    if (currentScreen == "login") {
                        LoginScreen(
                            userDao = userDao,
                            onLoginSuccess = { user -> loggedInUser = user },
                            onNavigateToRegister = { currentScreen = "register" }
                        )
                    } else {
                        RegisterScreen(
                            userDao = userDao,
                            onRegisterSuccess = { currentScreen = "login" },
                            onNavigateToLogin = { currentScreen = "login" }
                        )
                    }
                } else {
                    MainScreen(
                        user = loggedInUser!!,
                        onLogout = { loggedInUser = null },
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { isDarkMode = it }
                    )
                }
            }
        }
    }
}
