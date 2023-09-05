package com.example.worldskillstest

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.launch
import org.json.JSONObject

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChangePasswordScreen(navController: NavController, screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit) {
    var oldPassword by rememberSaveable { mutableStateOf("")}
    var newPassword by rememberSaveable { mutableStateOf("")}
    var confirmNewPassword by rememberSaveable { mutableStateOf("")}
    var modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden )
    var scaffoldState = rememberScaffoldState()
        Scaffold(topBar = {
            TopAppBar(backgroundColor = Red, elevation = 0.dp) {

                    Box(modifier = Modifier.fillMaxSize()) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "bACK", tint = Color.White, modifier = Modifier
                                .size(30.dp)
                                .align(
                                    Alignment.CenterStart
                                ))
                        }
                        Text(text = "Change Password", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                            Alignment.Center ))
                    }



            }
        }) {
            Spacer(modifier = Modifier.height(40.dp))
            Box {
                Spacer(modifier = Modifier
                    .fillMaxSize()
                    .background(Red))
                Column(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(20.dp))) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)){
                        MainTextField(value = oldPassword, onValueChange = {oldPassword = it}, label = "Old Password", visualTransformation = PasswordVisualTransformation())
                        MainTextField(value = newPassword, onValueChange = {newPassword = it}, label = "New Password", visualTransformation = PasswordVisualTransformation())
                        MainTextField(value = confirmNewPassword, onValueChange = {confirmNewPassword = it}, label = "Confirm new password", visualTransformation = PasswordVisualTransformation())
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    var scope = rememberCoroutineScope()
                    val context = LocalContext.current
                    Button(onClick = {
                        scope.launch {
                            onChangeScreenState(ScreenState.Loading)
                            var response: JSONObject? =  SharedPreferenceResolver(context).getUserSession()?.getString("accountid")
                                ?.let { it1 ->
                                   WorldSkillsAPI.changePassword(oldPassword, newPassword, confirmNewPassword,
                                        it1
                                    )
                                }
                            onChangeScreenState(ScreenState.Done)
                            Toast.makeText(context, response?.getJSONObject("result")?.getString("message"), Toast.LENGTH_LONG).show()

                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), shape = RoundedCornerShape(60.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Red)) {
                        Text(text = "Submit", color = Color.White, fontWeight =  FontWeight.SemiBold)
                    }

Spacer(modifier = Modifier.height(10.dp))
                }

            }

        }


}

@Preview(showBackground = true, device = Devices.NEXUS_7)
@Composable
fun P() {
   ChangePasswordScreen(navController = rememberNavController(), ScreenState.Done, {})
}