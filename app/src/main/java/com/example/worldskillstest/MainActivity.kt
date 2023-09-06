package com.example.worldskillstest

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.Red
import com.example.worldskillstest.ui.theme.WorldskillsTESTTheme

//169.254.73.126:8000
//2

enum class ScreenState {
    Done,
    Loading
}

fun countdownCallback(onFinish: () -> Unit) {

}
class MainActivity : ComponentActivity() {
    private lateinit var timer: CountDownTimer
    var loginCheck by mutableStateOf("login")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var screenState by rememberSaveable { mutableStateOf(ScreenState.Done)}
            var navController = rememberNavController()
            var destination = navController.currentBackStackEntryAsState()?.value?.destination?.route

            timer = remember {object: CountDownTimer(60000, 1000) {
                override fun onTick(p0: Long) {
                   println(p0)
                }

                override fun onFinish() {
                   SharedPreferenceResolver(applicationContext).clearUserSession()
                    navController.navigate("login")
                }

            }}
            LaunchedEffect(key1 = destination) {
                if(destination == "login") {
                    loginCheck = "login"
                    timer.cancel()

                } else {
                    println(destination)
                    loginCheck = "others"
                }
            }
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
                        composable("foodOutletDetails?ownercode={ownercode}") {
                          FoodOutletDetailsScreen(navController = navController,  ownerCode = it.arguments?.getString("ownercode")!!)
                        }
                        composable("foodSpecificDetails?merchant={merchant}") {
                            it.arguments?.getString("merchant")
                                ?.let { it1 -> FoodSpecificDetailsScreen(navController = navController, merchantCode = it1) }
                        }
                        composable("addingCalories?name={name}&calories={calories}&hex={hex}") {

                            it.arguments?.getString("name")?.let { it1 -> AddingCaloriesScreen(
                                navController = navController, name = it1, calories = it.arguments?.getString("calories"), hexData = it.arguments?.getString("hex")!!
                            ) }
                        }

                    }
                    AnimatedVisibility(visible = screenState != ScreenState.Done) {
                        LinearProgressIndicator(color = Red, modifier = Modifier
                            .fillMaxWidth()
                            .align(
                                Alignment.TopCenter
                            ))
                    }
                }


            }
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if(loginCheck != "login") {
            timer.cancel()
            timer.start()
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