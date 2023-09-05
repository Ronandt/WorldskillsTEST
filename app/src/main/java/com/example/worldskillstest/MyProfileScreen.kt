package com.example.worldskillstest

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.worldskillstest.ui.theme.Link
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.launch
import org.jetbrains.annotations.Async
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyProfileScreen(navController: NavController, screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit) {
    val context = LocalContext.current
    var session = SharedPreferenceResolver(context)
    var userInformation by remember { mutableStateOf(JSONObject().apply {
        put("accountid", "2")
        put("email", "")
        put("orgid", "")
    })}
    var launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
        if(it != null) {
            session.setUserPfp(uri = it)
            context.contentResolver.takePersistableUriPermission( it, Intent.FLAG_GRANT_READ_URI_PERMISSION,)
        }

    }


    var profilePicture by remember{mutableStateOf(
        SharedPreferenceResolver(context).getUserPfp()
    )}
    LaunchedEffect(key1 = Unit) {
        userInformation = session.getUserSession()!!
    }
    var bottomSheetOpened by remember {
        mutableStateOf(false)
    }
    var bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    Box() {
        androidx.compose.material.Scaffold(bottomBar = {
          NavBar(navController = navController)
        }, backgroundColor= Color.White){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Red))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally, ) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "My Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(14.dp))
                    Box() {
                        if(profilePicture == null) {
                            Image(painter =painterResource(R.drawable.profile_picture), contentDescription = "Profile picture", modifier = Modifier.size(100.dp))
                        } else {
                            AsyncImage(profilePicture, contentDescription = "Profile picture", modifier = Modifier.size(100.dp))
                        }


                        Card(shape = CircleShape, elevation = 100.dp, backgroundColor = Color.White, modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 10.dp, y = 10.dp)
                            .size(30.dp)
                            .clickable {
                                bottomSheetOpened = true
                            }) {
                            Icon(painterResource(id = R.drawable.outline_camera_alt_24), contentDescription = "Image", tint = Color.Gray, modifier = Modifier.requiredSize(20.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Card(elevation =  12.dp, backgroundColor = Color.White, modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 6.dp)) {
                                    Icon(Icons.Default.Person, contentDescription = "Person")
                                    Text(text = "Email")
                                }
                                Text(text = "${userInformation.getString("email")}", color = Color.Gray)

                            }
                            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 6.dp)) {
                                    Icon(painterResource(R.drawable.baseline_business_24), contentDescription = "Person")
                                    Text(text = "Organisation")

                                }
                                Text(text = "${userInformation.getString("orgid")}", color = Color.Gray)



                            }
                            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(vertical = 6.dp)) {
                                    Icon(Icons.Outlined.Lock, contentDescription = "Person")
                                    Text(text = "Password")
                                }
                                TextButton(onClick = { navController.navigate("changePassword") }) {
                                    Text(text = "Change Password", color = Color(0xFF8BB3EE))
                                }

                            }
                            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Card(elevation = 12.dp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .height(60.dp)) {
                        Column(
                            Modifier
                                .fillMaxSize()
                                .clickable {
                                    SharedPreferenceResolver(context).clearUserSession()
                                    navController.navigate("login")

                                }, verticalArrangement = Arrangement.Center) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Icon(Icons.Default.ExitToApp, contentDescription ="Logout", tint = Color.Gray )
                                Text(text = "Logout")
                            }
                        }
                    }
                }}
        }
        var view = LocalView.current
            AnimatedVisibility(visible = bottomSheetOpened) {
                Box() {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(listOf(Color.Black, Color.Black)),
                            alpha = 0.4f
                        )
                        .clickable {
                            bottomSheetOpened = false
                        }) {

                    }

                    Column(modifier = Modifier
                        .fillMaxWidth(0.94f)
                        .align(Alignment.BottomCenter)) {
                        androidx.compose.material3.Card(colors = CardDefaults.cardColors(Color.White), modifier = Modifier.height(120.dp)) {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment =   Alignment.CenterHorizontally) {
                                TextButton(onClick = { navController.navigate("camera") }) {
                                    Text(text = "Camera", color = Link, fontSize = 24.sp)
                                }

                                Divider(
                                    thickness = 1.dp,
                                    color = Color.LightGray,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                var scope = rememberCoroutineScope()
                                TextButton(onClick = { scope.launch  {
                                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                } }) {
                                    Text(text = "Photo Library", color = Link, fontSize = 24.sp)
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        androidx.compose.material3.Card(colors = CardDefaults.cardColors(Color.White), modifier = Modifier.height(60.dp)) {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment =   Alignment.CenterHorizontally) {
                                TextButton(onClick = { bottomSheetOpened = false }) {
                                    Text(text = "Cancel", color = Link, fontSize = 24.sp)
                                }

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }


            }

        if(bottomSheetOpened) {
            SideEffect {
                (view.context as Activity).window.statusBarColor   = Color(0xFF941E26).toArgb()
            }
        } else {
            SideEffect {
                (view.context as Activity).window.statusBarColor   = Red.toArgb()
            }
        }



    }



  }

@Preview(showBackground = true)
@Composable
fun C() {
   // MyProfileScreen(navController = rememberNavController(), screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit)
}