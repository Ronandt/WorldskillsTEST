package com.example.worldskillstest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.worldskillstest.ui.theme.Red
import org.json.JSONArray
import org.json.JSONObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodOutletDetailsScreen(navController: NavController,  ownerCode: String) {
    var stalls = remember {mutableStateListOf<Stalls>() }
    LaunchedEffect(Unit) {
        var response: JSONArray =
            WorldSkillsAPI.getFBMerchants(ownerCode).getJSONObject("result").getJSONArray("data")
        for (x in (0..response.length() -1)) {
            var obj =response.getJSONObject(x)
            stalls.add(Stalls(id = obj.getString("id"), name = obj.getString("name"), obj.getString("merchantcode"), obj.getString("img")))
        }
        println(stalls.toList())


    }
    Scaffold(topBar =  {
        MainTopBar(navController = navController, text = "${ownerCode}")
    }) {
       Column(modifier = Modifier.padding(it)) {
           Column(modifier = Modifier.fillMaxWidth()) {
               LazyColumn {
                   items(stalls.size) {
                       Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically, modifier =Modifier.clickable {
                           navController.navigate("foodSpecificDetails?merchant=${stalls[it].merchantcode}")
                       }.padding(vertical = 30.dp, horizontal = 12.dp),) {

                           Text(text = "${stalls[it].name}", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)

                       }
                       Divider()
                   }

               }
           }
       }
    }
}