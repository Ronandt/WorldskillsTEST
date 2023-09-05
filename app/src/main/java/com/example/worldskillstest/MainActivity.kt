package com.example.worldskillstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.WorldskillsTESTTheme
//169.254.73.126:8000
//2
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var navController = rememberNavController()
            LaunchedEffect(Unit) {

            }
            WorldskillsTESTTheme {
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(navController)
                    }
                    composable("changePassword") {
                        ChangePasswordScreen(navController)
                    }
                    composable("myProfile") {
                        MyProfileScreen(navController)
                    }
                    composable("colorMyDiet") {
                        ColorMyDietScreen(navController = navController)
                    }
                    composable("addMyCalories") {
                        AddMyCaloriesScreen(navController = navController)
                    }
                    composable("camera") {
                        CameraScreen(navController = navController, activity = this@MainActivity, baseContext = baseContext                 )
                    }
                }
            }
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
    WorldskillsTESTTheme {
        Greeting("Android")
    }
}