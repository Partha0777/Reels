package com.curiozing.reels

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.curiozing.reels.composeUi.CreateReel
import com.curiozing.reels.composeUi.Home
import com.curiozing.reels.composeUi.Profile
import com.curiozing.reels.ui.theme.ReelsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppContent() {
    val navController = rememberNavController()
    ReelsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
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
}


@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val bottomItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.CreateReel,
        BottomNavigationItem.Profile,
    )
    val currentScreen = navController.currentBackStackEntryAsState().value?.destination

    BottomNavigation {
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
                    Icon(imageVector = it.icon, contentDescription = "")
                })
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