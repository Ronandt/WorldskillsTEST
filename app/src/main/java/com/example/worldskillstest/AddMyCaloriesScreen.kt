package com.example.worldskillstest

import android.Manifest
import android.annotation.SuppressLint
import android.location.LocationManager
import android.os.Build
import android.webkit.WebView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.worldskillstest.WorldSkillsAPI.baseUrl
import com.example.worldskillstest.ui.theme.Red
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Integer.parseInt
import kotlin.reflect.KProperty

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMyCaloriesScreen(navController: NavController, screenState: ScreenState, onChangeScreenState: (ScreenState) -> Unit)   {
    var allow by remember {mutableStateOf(true)}
    var jsonResponse by remember {mutableStateOf(JSONObject())}
    var longitude by remember {mutableStateOf(0.0)}
    var latitude by remember {mutableStateOf(0.0)}
    var merchantList = remember { mutableStateListOf<Merchant>()}
    var done by remember {mutableStateOf(false)}
    var list: JSONArray = JSONArray()

    var context = LocalContext.current
    val launch = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) {
        if(allow) {
            allow = it
        }




    }

    LaunchedEffect(key1 = Unit ) {
        println("Are they launching")

        launch.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        launch.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        if(allow) {
            var locationManager = context.getSystemService(LocationManager::class.java)
            var location = locationManager.getLastKnownLocation(locationManager.allProviders[0])
            latitude = location?.latitude!!
            longitude = location?.longitude!!
            println(SharedPreferenceResolver(context).getUserSession())
            var session = SharedPreferenceResolver(context).getUserSession()?.getString("orgid")
            if (latitude != null) {
                println("HI")

               list = WorldSkillsAPI.getLocations(lat = latitude, lng = longitude!!, parseInt(session  )).getJSONObject("result").getJSONArray("data")
                for(x in (0..list.length() -1)) {
                    merchantList.add(Merchant(list.getJSONObject(x).getDouble("lat"), lng = list.getJSONObject(x).getDouble("lng"), name = list.getJSONObject(x).getString("name"), list.getJSONObject(x).getString("ownercode")))
                }
                println(merchantList.toList())
                done = true
            }




        }



    }

    androidx.compose.material.Scaffold(bottomBar = {
      NavBar(navController = navController)
    }, topBar =  { MainTopBar(navController = navController, text ="F&B Near Me" )}) {
        if(!allow) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Allow locations in ap to locate nearby F&B merchants")
            }
        } else {
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                if(done) {

                    AndroidView(factory = {
                        WebView(it).apply {
                            settings.javaScriptEnabled = true

                            loadUrl("https://developers.onemap.sg/comm" +
                                    "onapi/staticmap/getStaticImage?" +
                                    "layerchosen=default&lat=1.30980&lng=103.77750&zoom=14&height=512" +
                                    "&width=400&points=[${merchantList.getOrNull(0)?.lat}," +
                                    "${merchantList.getOrNull(0)?.lng},\"175,50,0\",\"A\"]|" +
                                    "[${merchantList.getOrNull(1)?.lat},${merchantList.getOrNull(1)?.lng},\"255,255,178\",\"" +
                                    "B\"]|" +
                                    "[${merchantList.getOrNull(2)?.lat},${merchantList.getOrNull(2)?.lng},\"175,50,0\",\"C\"]")

                        }
                    }, update = {}, modifier = Modifier.fillMaxWidth()
                        .height(400.dp))
                }

                /*Image(painterResource(R.drawable.maps) , contentDescription = "Map image", modifier = Modifier
                  , contentScale = ContentScale.Crop)*/
                Column(modifier = Modifier.fillMaxWidth()) {
                    LazyColumn {
                        items(merchantList.size) {
                            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .clickable {
                                    navController.navigate("foodOutletDetails?ownercode=${merchantList[it].ownerCode}")
                                }
                                .padding(vertical = 20.dp, horizontal = 12.dp)) {
                                when(it) {
                                    0 -> {
                                        Text(text = "A", color = Red, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                                    }
                                    1 -> {
                                        Text(text = "B", color = Red, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                                    }
                                    2-> {
                                        Text(text = "C", color = Red, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                                    }
                                }

                                Text(text = merchantList[it].name, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)

                            }
                            Divider()
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
fun a() {
    AddMyCaloriesScreen(navController = rememberNavController(), screenState = ScreenState.Done, onChangeScreenState = {})
}