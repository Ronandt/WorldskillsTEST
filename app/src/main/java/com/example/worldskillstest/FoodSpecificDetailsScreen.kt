package com.example.worldskillstest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ChipColors
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.ChipColour
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSpecificDetailsScreen(navController: NavController, merchantCode: String) {
    var menuItems = remember{ mutableStateListOf<MenuItem>() }
    LaunchedEffect(key1 = Unit) {
        var response: JSONArray= WorldSkillsAPI.getFBMenuItems(merchantCode).getJSONObject("result").getJSONArray("data")
        for(x in (0..response.length() -1)) {
            var obj = response.getJSONObject(x)
            menuItems.add(MenuItem(name = obj.getString("name"), id = obj.getInt("id"), img = obj.getString("img"),
                calorie = obj.getString("calorie"), color = obj.getString("color"), description = obj.getString("description"), price = obj.getString("price"), addon = listOf(), category = listOf(obj.getJSONArray("category").getString(0)) ))
        }
        println("HUH")
        println(menuItems.toList())
    }
    Scaffold(topBar =  {
        MainTopBar(navController = navController, text = "Stall 1")
    }) {
        Column(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(){
                    items(menuItems.size) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                            navController.navigate("addingCalories?name=${menuItems[it].name}&calories=${menuItems[it].calorie}&hex=${menuItems[it].color}")
                        }
                            .padding(vertical = 30.dp, horizontal = 16.dp)
                            .fillMaxWidth()) {

                            Text(text = "${menuItems[it].name}", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
                            Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp), colors = CardDefaults.cardColors(containerColor = ChipColour), modifier = Modifier.width(100.dp)) {
                                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "${menuItems[it].calorie}", color =  Color.White)
                                }

                            }

                        }
                        Divider()
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun z() {
    FoodSpecificDetailsScreen(navController = rememberNavController(), "me")
}