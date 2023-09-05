package com.example.worldskillstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.Red
import com.example.worldskillstest.ui.theme.WorldskillsTESTTheme
//169.254.73.126:8000
//2

enum class ScreenState {
    Done,
    Loading
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screenState by rememberSaveable { mutableStateOf(ScreenState.Done)}
            var navController = rememberNavController()
            WorldskillsTESTTheme {
                Box() {

                    // A surface container using the 'background' color from the theme
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, screenState, {screenState = it})
                        }
                        composable("changePassword") {
                            ChangePasswordScreen(navController, screenState, {screenState = it})
                        }
                        composable("myProfile") {
                            MyProfileScreen(navController, screenState, {screenState = it})
                        }
                        composable("colorMyDiet") {
                            ColorMyDietScreen(navController = navController, screenState, {screenState = it})
                        }
                        composable("addMyCalories") {
                            AddMyCaloriesScreen(navController = navController, screenState, {screenState = it})
                        }
                        composable("camera") {
                            CameraScreen(navController = navController, activity = this@MainActivity, baseContext = baseContext , screenState, {screenState = it})
                        }
                    }
                    AnimatedVisibility(visible = screenState != ScreenState.Done) {
                        LinearProgressIndicator(color = Red, modifier = Modifier.fillMaxWidth().align(
                            Alignment.TopCenter))
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