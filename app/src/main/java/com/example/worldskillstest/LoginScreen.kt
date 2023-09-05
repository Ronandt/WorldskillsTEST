package com.example.worldskillstest

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    var scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("devops2@worldskills.sg")}
    var password by remember { mutableStateOf("P@ssw0rd")}
    //var isEmail by remember {mutableStateOf(false)}
    var firstTime by remember {mutableStateOf(true)}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment =  Alignment.CenterHorizontally) {
        Image(painterResource(id = R.drawable.worldskills_logo), contentDescription = "World skills Logo", Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(30.dp))
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            MainTextField(value = email, onValueChange ={email = it
                                                        firstTime = false
                                                        }, label = "Email", error = !firstTime && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())

            Spacer(modifier = Modifier.height(6.dp))
            MainTextField(value = password, onValueChange ={password = it}, label = "Password", visualTransformation = PasswordVisualTransformation())

        }

        Spacer(modifier = Modifier.height(60.dp))
        val context = LocalContext.current

        Button(onClick = {
            scope.launch {
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(context, "Invalid email", Toast.LENGTH_LONG).show()
                } else {
                    println("Test")
                    var response =  WorldSkillsAPI.login(email, password, 2)
                    Toast.makeText(context, if(response.getString("status") == "success") "Logged in" else response.getJSONObject("result").getString("message"), Toast.LENGTH_LONG).show()
                    if(response.getString("status") == "success") {
                        SharedPreferenceResolver(context).setUserSession(response.getJSONObject("result"))
                        navController.navigate("colorMyDiet")
                    }
                }

            }

          }, modifier = Modifier
            .fillMaxWidth()
            .height(50.dp), shape = RoundedCornerShape(60.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Red)) {

            Text(text = "Login", color = Color.White, fontWeight =  FontWeight.SemiBold, fontSize = 18.sp)
        }
    }
}
