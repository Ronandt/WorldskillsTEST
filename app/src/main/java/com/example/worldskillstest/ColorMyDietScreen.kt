package com.example.worldskillstest

import android.Manifest
import android.os.Build
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
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
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.worldskillstest.ui.theme.ChipColour
import com.example.worldskillstest.ui.theme.Red
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ColorMyDietScreen(navController: NavController, screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit, dialogBackground: (Boolean) -> Unit) {
 //   var dialogOpened by remember { mutableStateOf(false )}
    var selected by remember {mutableStateOf("1M")}

    var orientation = Orientation.Horizontal
    //var fractionalThreshold = {_, _ -> FractionalThreshold(0.5f)}

    var context = LocalContext.current
    var foodItems  = remember {mutableStateListOf<FoodItem>()}
    var highCalorieCalc by remember {mutableStateOf(0)}
    var midCalorieCalc by remember{mutableStateOf(0)}
    var lowCalorieCalc by remember{ mutableStateOf(0)}
    var timecalc by remember {mutableStateOf(1L)}
    var currentTime = remember {Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()) }
    var beforeTime = currentTime.minusMonths(timecalc).toEpochSecond()
    var latestTime = currentTime.toEpochSecond()
    var update by remember{mutableStateOf(false)}
    var d = SimpleDateFormat("dd/MM/yyyy")
    LaunchedEffect(update, timecalc) {
      var  lowCalorieUnc = 0
        var midCalorieUnc = 0
      var  highCalorieUnc = 0

        foodItems.clear()
        withContext(Dispatchers.IO) {

           Db.getInstance(context = context).foodItemDao().getAll().forEach {
               foodItems.add(it)
               println((d.parse(it.date).toInstant().toEpochMilli()/1000).toString() + "CuRRENT TIME")
               println(beforeTime.toString() +"BEFORE TIME")
               println(latestTime.toString() + "LATEST TIME")
               if(d.parse(it.date).toInstant().toEpochMilli()/1000 > beforeTime && d.parse(it.date).toInstant().toEpochMilli()/1000 < latestTime) {
                   when(it.hex) {
                       "#008450" -> {
                           lowCalorieUnc +=1
                       }
                       "#EFB700" -> {
                           midCalorieUnc +=1
                       }
                       "#B81D13" ->{
                           highCalorieUnc += 1
                       }

                   }
               }
               lowCalorieCalc = lowCalorieUnc
               midCalorieCalc = midCalorieUnc
               highCalorieCalc = highCalorieUnc



           }
        }

    }
    androidx.compose.material.Scaffold(bottomBar = {
        NavBar(navController = navController)
    }, topBar = {
        MainTopBar(navController = navController, text ="ColorMyDiet", back = false)
    }) {

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
                            .height(30.dp), onClick = {selected = "1M"
                                                      timecalc = 1L}, colors = CardDefaults.cardColors(containerColor = if(selected == "1M")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "1M", color = if(selected == "1M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), colors = CardDefaults.cardColors(containerColor = if(selected == "3M")Red else Color.White), onClick = {
                                selected = "3M"
                            timecalc = 3
                        }) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "3M", color = if(selected == "3M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), onClick = {selected = "6M"
                                                      timecalc = 6}, colors = CardDefaults.cardColors(containerColor = if(selected == "6M")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "6M", color = if(selected == "6M")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(modifier = Modifier
                            .width(100.dp)
                            .height(30.dp), onClick = {selected = "1Y"
                                                      timecalc = 12},  colors = CardDefaults.cardColors(containerColor =if(selected == "1Y")Red else Color.White)) {
                            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
                                Text(text = "1Y", color = if(selected == "1Y")Color.White else Color.Black)
                            }
                        }

                        Text(text = "|")
                    }


                }




            }


            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment =Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {

                    Spacer(modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF86080C))
                        .clip(CircleShape))
                    Text(text = "High Calories")
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {

                    Spacer(modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFC58F07))
                        .clip(CircleShape))
                    Text(text = "Mid Calories")
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {

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
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp), verticalAlignment = Alignment.Bottom, modifier = Modifier.height(150.dp)){

                Spacer(modifier = Modifier
                    .animateContentSize()
                    .height(
                        (140 / maxOf(
                            lowCalorieCalc,
                            midCalorieCalc,
                            highCalorieCalc,
                            1
                        ) * highCalorieCalc).dp
                    )
                    .width(60.dp)
                    .background(Color(0xFF86080C)))
                Spacer(modifier = Modifier
                    .animateContentSize()
                    .height(
                        (140 / maxOf(
                            lowCalorieCalc,
                            midCalorieCalc,
                            highCalorieCalc,
                            1
                        ) * midCalorieCalc).dp
                    )
                    .width(60.dp)
                    .background(Color(0xFFC58F07)))
                Spacer(modifier = Modifier
                    .animateContentSize()
                    .height(
                        (140 / maxOf(
                            lowCalorieCalc,
                            midCalorieCalc,
                            highCalorieCalc,
                            1
                        ) * lowCalorieCalc).dp
                    )
                    .width(60.dp)
                    .background(ChipColour))
            }

            Divider(modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(40.dp)){
                Text(text = highCalorieCalc.toString())
                Text(text =  midCalorieCalc.toString())
                Text(text = lowCalorieCalc.toString())
            }
            Spacer(modifier = Modifier.height(20.dp))
            androidx.compose.material.Card(modifier = Modifier.fillMaxSize(), elevation = 20.dp){
                LazyColumn(){
                    items(foodItems.size) {
                        var dialogOpened by remember {mutableStateOf(false)}
                        var scope = rememberCoroutineScope()
                        var deleted by remember {mutableStateOf(false)}
                        var swipeableState = rememberSwipeableState(initialValue = 0)
                        if(dialogOpened) {

                            AlertDialog(onDismissRequest = { dialogOpened = false
                                                           dialogBackground(false)}, title = {
                                Text(text = "Delete Item?", fontWeight = FontWeight.SemiBold, color = Red)

                            },text = {

                                Text(text = "${foodItems[it].name}", color = Color.Black, modifier=  Modifier.offset(x = 50.dp, y = 20.dp))}, confirmButton = {
                                TextButton(onClick = { scope.launch {
                                    withContext(Dispatchers.IO) {
                                        Db.getInstance(context).foodItemDao().removeItem(foodItems[it])
                                    }
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_LONG).show()
                                    dialogOpened = false
                                   dialogBackground(false)
                                    deleted = true
                                    when(foodItems[it].hex) {
                                        "#008450" -> {
                                            lowCalorieCalc -=1
                                        }
                                        "#EFB700" -> {
                                            midCalorieCalc -=1
                                        }
                                        "#B81D13" ->{
                                            highCalorieCalc -= 1
                                        }

                                    }

                                }}, colors = ButtonDefaults.textButtonColors(contentColor = Red)) {
                                    Text(text = "Confirm")
                                }
                            }, dismissButton = {
                                TextButton(onClick = { dialogOpened = false
                                                     dialogBackground(false)}, colors = ButtonDefaults.textButtonColors(contentColor = Red)) {
                                    Text(text = "Cancel")
                                }
                            })
                        }
                        if(!deleted) {
                            LaunchedEffect(key1 =  swipeableState.currentValue ) {
                                if(swipeableState.currentValue == 1) {
                                    withContext(Dispatchers.IO) {
                                        Db.getInstance(context).foodItemDao().removeItem(foodItems[it])
                                    }

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
                                    .clickable {
                                        dialogBackground(true)
                                        dialogOpened = true
                                    }
                                    .padding(top = 6.dp, bottom = 30.dp)
                                    .padding(top = 0.dp)
                                    .padding(start = 10.dp)
                                    .fillMaxWidth()) {

                                    Column( verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)) {
                                        Text(text = "${foodItems[it].name}", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
                                        Text(text = "Last updated by: ${foodItems[it].date}", fontSize = 12.sp, color = Color.Gray)
                                    }

                                    Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp), colors = CardDefaults.cardColors(containerColor = Color(foodItems[it].hex.toColorInt())), modifier = Modifier.width(90.dp).offset(x = -5.dp)) {
                                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(text = "${foodItems[it].calories.split(".")[0] + "Cal"}", color =  Color.White)

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
    AlertDialog(onDismissRequest = {  }, title = {
        Text(text = "Delete Item?", fontWeight = FontWeight.SemiBold, color = Red)

    },text = {

        Text(text = "Chicken Rice", color = Color.Black, modifier=  Modifier.offset(x = 50.dp, y = 20.dp))}, confirmButton = {
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.textButtonColors(contentColor = Red)) {
            Text(text = "Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.textButtonColors(contentColor = Red)) {
            Text(text = "Cancel")
        }
    })
}
