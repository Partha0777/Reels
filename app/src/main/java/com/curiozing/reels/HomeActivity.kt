package com.curiozing.reels

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cloudinary.android.MediaManager
import com.curiozing.reels.AppKeysAndBaseUrl.CLOUDINARY_API_KEY
import com.curiozing.reels.AppKeysAndBaseUrl.CLOUDINARY_API_SECRET
import com.curiozing.reels.AppKeysAndBaseUrl.CLOUDINARY_CLOUD_NAME
import com.curiozing.reels.composeUi.CreateReel
import com.curiozing.reels.composeUi.Home
import com.curiozing.reels.composeUi.MyReels
import com.curiozing.reels.composeUi.Profile
import com.curiozing.reels.ui.theme.ContentColor
import com.curiozing.reels.ui.theme.ReelsTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCloudinaryConfig(this)
        setContent {
            ReelsTheme {
                AppScreenNavigator()
            }
        }
    }
}

fun initCloudinaryConfig(context: Context) {
    val config: MutableMap<String, String> = HashMap()
    config["cloud_name"] = CLOUDINARY_CLOUD_NAME
    config["api_key"] = CLOUDINARY_API_KEY
    config["api_secret"] = CLOUDINARY_API_SECRET
    MediaManager.init(context, config)
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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = true) {
                delay(3000)
                navController.navigate("main") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
            Text(
                text = "Reels",
                style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
            )
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
            AppContent(navigateController)
        }
        composable("CreateReel") {
            CreateReel()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppContent(mainNavigateController: NavController) {
    val navController = rememberNavController()
    Surface {
        val context = LocalContext.current
        val view = LocalView.current

        val window = (context as ComponentActivity).window
        window.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true

        Scaffold(bottomBar = {
            BottomBarNavigation(navController)
        }) {
            NavHost(
                navController = navController, startDestination = BottomNavigationItem.Home.route
            ) {
                composable(BottomNavigationItem.Home.route) {
                    Home()
                }
                composable(BottomNavigationItem.CreateReel.route) {
                    MyReels {
                        mainNavigateController.navigate("CreateReel")
                    }
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
    Box {
        BottomNavigation(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 18.dp)
                .clip(RoundedCornerShape(80)), backgroundColor = MaterialTheme.colorScheme.primary
        ) {
            bottomItems.forEach {
                BottomNavigationItem(selected = it.route == currentScreen?.route, onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }, icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = ContentColor
                    )
                })
            }
        }
    }
}

sealed class BottomNavigationItem(val name: String, val route: String, val icon: Int) {
    data object Home : BottomNavigationItem("Home", "home", R.drawable.home_icon)
    data object CreateReel :
        BottomNavigationItem("Create", "createReels", R.drawable.video_recording_icon)

    data object Profile : BottomNavigationItem("Profile", "profile", R.drawable.profile_icon)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}