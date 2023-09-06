package com.example.worldskillstest

import android.os.Build
import android.widget.DatePicker
import android.widget.DatePicker.OnDateChangedListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Integer.parseInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingCaloriesScreen(navController: NavController, name: String, calories: String?, hexData: String) {
    var itemName by remember {mutableStateOf(name)}
    var calories by remember {mutableStateOf(calories)}
    var dateConsumed by remember {mutableStateOf("")}
    var openDatePicker by remember {mutableStateOf(false)}
    var date by remember {mutableStateOf("")}
    Scaffold(topBar =  {
        MainTopBar(navController = navController, text = "Add My Calories")
    }) {
        Column(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(20.dp))) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)){
                        MainTextField(value =  "", onValueChange = {itemName = ""}, label = itemName, readonly = true)
                        calories?.let { it1 -> MainTextField(value = "",  onValueChange = {},label = it1, readonly = true) }
                        Column(modifier = Modifier.clickable {
                            openDatePicker = !openDatePicker
                        }) {
                            MainTextField(value = date, onValueChange = {}, label = "Enter Date Consumed", readonly = true)
                            Button(onClick = { openDatePicker = !openDatePicker}, colors = ButtonDefaults.buttonColors(Red)) {
                                Text(text = "Open date picker", color = Color.White)
                            }
                        }

                    }
                    Text(hexData)
                    if(openDatePicker) {
                        AndroidView(factory = {
                            return@AndroidView DatePicker(it).apply {
                                setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                                    date = "$dayOfMonth/$monthOfYear/$year"

                                }
                            }
                        }, update = {})

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    var scope = rememberCoroutineScope()
                    val context = LocalContext.current
                    Button(onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                calories?.let { it1 -> FoodItem(name = name, calories = it1, date = date, hex =hexData ) }
                                    ?.let { it2 ->
                                        Db.getInstance(context).foodItemDao().insertFoodItem(it2)
                                    }
                            }

                            navController.navigateUp()
                            Toast.makeText(context, "Item added", Toast.LENGTH_LONG).show()
                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp), shape = RoundedCornerShape(60.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Red)) {
                        Text(text = "Submit", color = Color.White, fontWeight =  FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Spacer(modifier = Modifier
                        .height(150.dp)
                        .background(Color.White))

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun i() {
   // AddingCaloriesScreen(navController = rememberNavController(), "", "34", "")
}