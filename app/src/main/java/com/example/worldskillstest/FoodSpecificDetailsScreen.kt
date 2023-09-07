package com.example.worldskillstest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ChipColors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.ChipColour
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
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
                        var state = rememberSwipeableState(initialValue = 0)
                        LaunchedEffect(state.currentValue) {
                            if(state.currentValue == 1) {
                                navController.navigate(
                                    "addingCalories?name=${menuItems[it].name}&calories=${menuItems[it].calorie}&hex=${
                                        URLEncoder.encode(menuItems[it].color)
                                    }"
                                )
                            }
                        }

                        Box(modifier = Modifier.swipeable(state = state, anchors = mapOf(0f to 0, 1000f to 1),orientation = Orientation.Horizontal )) {
                            Column(modifier = Modifier.offset {
                                IntOffset(x = state.offset.value.toInt(), y = 0)
                            }) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                    .clickable {
                                        navController.navigate(
                                            "addingCalories?name=${menuItems[it].name}&calories=${menuItems[it].calorie}&hex=${
                                                URLEncoder.encode(menuItems[it].color)
                                            }"
                                        )
                                    }
                                    .padding(vertical = 30.dp, horizontal = 16.dp)
                                    .fillMaxWidth()) {

                                    Text(text = "${menuItems[it].name}", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
                                    Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp), colors = CardDefaults.cardColors(containerColor = Color(menuItems[it].color.toColorInt())), modifier = Modifier.width(100.dp)) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "${menuItems[it].calorie.split(".")[0] + "Cal"}", color =  Color.White)
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
    }
}

@Preview(showBackground = true)
@Composable
fun z() {
    FoodSpecificDetailsScreen(navController = rememberNavController(), "me")
}