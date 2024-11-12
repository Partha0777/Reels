package com.curiozing.reels

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.curiozing.reels.composeUi.CreateReel
import com.curiozing.reels.composeUi.Home
import com.curiozing.reels.composeUi.Profile
import com.curiozing.reels.ui.theme.ReelsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReelsTheme {
                AppScreenNavigator()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppSplashScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        val context = LocalContext.current
        val window = (context as ComponentActivity).window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Hello Splash")
            LaunchedEffect(key1 = true) {
                delay(3000)
                navController.navigate("main") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreenNavigator() {
    val navigateController = rememberNavController()

    NavHost(navController = navigateController, startDestination = "splash") {

        composable("splash") {
            AppSplashScreen(navController = navigateController)
        }

        composable("main") {
            AppContent()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppContent() {
    val navController = rememberNavController()
    Surface {
        val context = LocalContext.current
        val view = LocalView.current

        val window = (context as ComponentActivity).window
        window.statusBarColor = Color(0xFFFFFFFF).toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true


        Scaffold(
            bottomBar = {
                BottomBarNavigation(navController)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomNavigationItem.Home.route
            ) {
                composable(BottomNavigationItem.Home.route) {
                    Home()
                }
                composable(BottomNavigationItem.CreateReel.route) {
                    CreateReel()
                }
                composable(BottomNavigationItem.Profile.route) {
                    Profile()
                }
            }
        }
    }
}


@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val bottomItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.CreateReel,
        BottomNavigationItem.Profile,
    )
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination
    Box (
       // Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp).background(color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(50.dp)),
    ){
        BottomNavigation(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp).clip(RoundedCornerShape(80)),
            backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            bottomItems.forEach {
                BottomNavigationItem(
                    selected = it.route == currentScreen?.route,
                    onClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(imageVector = it.icon, contentDescription = "", modifier = Modifier.size(32.dp))
                    })
            }
        }
    }
}


sealed class BottomNavigationItem(val name: String, val route: String, val icon: ImageVector) {
    data object Home : BottomNavigationItem("Home", "home", Icons.Default.Home)
    data object CreateReel : BottomNavigationItem("Create", "createReels", Icons.Default.PlayArrow)
    data object Profile : BottomNavigationItem("Profile", "profile", Icons.Default.Person)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReelsTheme {
        AppContent()
    }
}