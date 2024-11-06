package com.curiozing.reels

import android.annotation.SuppressLint
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
fun AppContent(){
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
               NavHost(navController = navController, startDestination = "home"){
                   composable("home") {
                       Home()
                   }
                   composable("createReel") {
                       CreateReels()
                   }
                   composable("profile") {
                       Profile()
                   }
               }
           }

        }
    }
}

@Composable
fun Home() {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Home")
    }
}

@Composable
fun CreateReels() {
    Text(text = "Reels")
}

@Composable
fun Profile() {
    Text(text = "Profile")
}


@Composable
fun BottomBarNavigation(navController: NavHostController) {
    BottomNavigation {
        repeat(3){
            BottomNavigationItem(selected = false,
                onClick = {
                          if(it == 1){
                              navController.navigate("createReel")
                          }
                },
                icon = {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "")
                })
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReelsTheme {
        Greeting("Android")
    }
}