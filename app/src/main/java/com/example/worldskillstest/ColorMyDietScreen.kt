package com.example.worldskillstest

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.ChipColour
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ColorMyDietScreen(navController: NavController, screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit) {
    var dialogOpened by remember { mutableStateOf(false )}
    var selected by remember {mutableStateOf("1M")}

    var orientation = Orientation.Horizontal
    //var fractionalThreshold = {_, _ -> FractionalThreshold(0.5f)}

    var context = LocalContext.current
    var foodItems  = remember {mutableStateListOf<FoodItem>()}
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
           Db.getInstance(context = context).foodItemDao().getAll().forEach {
               foodItems.add(it)
           }
        }

    }
    androidx.compose.material.Scaffold(bottomBar = {
        NavBar(navController = navController)
    }, topBar = {
        MainTopBar(navController = navController, text ="ColorMyDiet", back = false)
    }) {
        if(dialogOpened) {
            AlertDialog(onDismissRequest = { dialogOpened = false }, title = {
                                                                             Text(text = "Delete Item?", fontWeight = FontWeight.SemiBold, color = Color.Blue)
            }, buttons = {

                Row {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text(text = "Cancel", color = Color.Blue)
                    }
                }
            })
        }
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            ,  horizontalAlignment = Alignment.CenterHorizontally) {

           Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(6.dp))) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), colors = CardDefaults.cardColors(containerColor = if(selected == "1M")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "1M", color = if(selected == "1M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), colors = CardDefaults.cardColors(containerColor = if(selected == "2M")Red else Color.White), onClick = {
                                selected = "2M"
                        }) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "2M", color = if(selected == "2M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), onClick = {selected = "3M"}, colors = CardDefaults.cardColors(containerColor = if(selected == "3M")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "3M", color = if(selected == "3M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), onClick = {selected = "3M"},  colors = CardDefaults.cardColors(containerColor =if(selected == "4M")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "4M", color = if(selected == "4M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }


                }




            }


            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment =Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF86080C))
                        .clip(CircleShape))
                    Text(text = "High Calories")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC58F07))
                        .clip(CircleShape))
                    Text(text = "Mid Calories")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(ChipColour)
                        .clip(CircleShape))
                    Text(text = "Healthier Choice")
                }



            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "No. of Food Items", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){
                Spacer(modifier = Modifier
                    .height(140.dp)
                    .width(60.dp)
                    .background(Color(0xFF86080C)))
                Spacer(modifier = Modifier
                    .height(140.dp)
                    .width(60.dp)
                    .background(Color(0xFFC58F07)))
                Spacer(modifier = Modifier
                    .height(140.dp)
                    .width(60.dp)
                    .background(ChipColour))
            }
            Divider(modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)){
                Text(text = "15")
                Text(text = "15")
                Text(text = "15")
            }
            Spacer(modifier = Modifier.height(20.dp))
            androidx.compose.material.Card(modifier = Modifier.fillMaxSize(), elevation = 20.dp){
                LazyColumn(){
                    items(foodItems.size) {

                        var swipeableState = rememberSwipeableState(initialValue = 0)
                        if(swipeableState.currentValue != 1) {
                            LaunchedEffect(key1 =  swipeableState.currentValue ) {
                                if(swipeableState.currentValue == 1) {
                                    Db.getInstance(context).foodItemDao().removeItem(foodItems[it])
                                }
                            }

                            if(it == 0) {
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            if(it!=0) {
                                androidx.compose.material3.Divider()
                            }

                            Box(modifier = Modifier.swipeable(state = swipeableState, anchors = mapOf(0f to 0, 1000f to 1), orientation = orientation) ) {
                                Row( horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                    .offset {
                                        IntOffset(x = swipeableState.offset.value.toInt(), y = 0)
                                    }
                                    .padding(top = 6.dp, bottom = 30.dp)
                                    .padding(top = 0.dp)
                                    .padding(start = 10.dp)
                                    .fillMaxWidth()) {

                                    Column( verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)) {
                                        Text(text = "${foodItems[it].name}", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
                                        Text(text = "${foodItems[it].date}", fontSize = 12.sp, color = Color.Gray)
                                    }

                                    Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp), colors = CardDefaults.cardColors(containerColor = ChipColour), modifier = Modifier.width(100.dp)) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "${foodItems[it].calories}", color =  Color.White)

                                        }

                                    }

                                }
                            }
                        }



                    }

                }
            }


        }
    }

}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun Vb() {
    ColorMyDietScreen(navController = rememberNavController(), screenState =ScreenState.Done , onChangeScreenState = {})
}
