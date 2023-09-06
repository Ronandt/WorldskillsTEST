package com.example.worldskillstest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.worldskillstest.ui.theme.Red

@Composable
fun MainTextField(
    value: String,onValueChange: (String) -> Unit,label: String, visualTransformation: VisualTransformation = VisualTransformation.None, error: Boolean = false, readonly: Boolean = false
) {
    TextField(value = value, isError = error, onValueChange =onValueChange, readOnly = readonly,modifier = Modifier.fillMaxWidth(), label = { Text(
        text = label, fontWeight = FontWeight.Bold, color =  Color(0xFFBBBABA), fontSize = 18.sp
    )
    }, visualTransformation = visualTransformation, colors = TextFieldDefaults.textFieldColors(
        Red, focusedLabelColor = Red, focusedIndicatorColor =    Color(
            0xFFEEEDED
        )  , unfocusedIndicatorColor =   Color(
            0xFFEEEDED
        ), backgroundColor =   Color(
            0xFFEEEDED
        ),
    ), shape =  RoundedCornerShape( 6.dp))

}

@Composable
fun NavBar(navController: NavController) {
    BottomNavigation(backgroundColor = Color.White) {
        BottomNavigationItem(selected = navController.currentBackStackEntryAsState().value?.destination?.route == "colorMyDiet", onClick = {navController.navigate("colorMyDiet") }, icon = { Icon(
            Icons.Outlined.Home,
            contentDescription = "ColorMyDiet",
            tint = if(navController.currentBackStackEntryAsState().value?.destination?.route == "colorMyDiet")Red else Color.Black
        )
        }, label = { androidx.compose.material3.Text(
            text = "ColorMyDiet",fontSize = 10.sp,
            color = if(navController.currentBackStackEntryAsState().value?.destination?.route == "colorMyDiet")Red else Color.Black
        )
        }, selectedContentColor = if(navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile")Red else Color.Black)
        BottomNavigationItem(selected = navController.currentBackStackEntryAsState().value?.destination?.route == "addMyCalories", onClick = {navController.navigate("addMyCalories") }, icon = { Icon(
            painterResource(id = R.drawable.outline_sticky_note_2_24),
            contentDescription = "Add My Calories",
            tint = if(navController.currentBackStackEntryAsState().value?.destination?.route == "addMyCalories")Red else Color.Black
        ) }, label = { androidx.compose.material3.Text(
            text = "Add My Calories", fontSize = 10.sp,
            color = if(navController.currentBackStackEntryAsState().value?.destination?.route == "addMyCalories")Red else Color.Black
        )
        }, selectedContentColor = if(navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile")Red else Color.Black,)
        BottomNavigationItem(selected = navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile", onClick = {navController.navigate("myProfile") }, icon = { Icon(Icons.Default.Person, contentDescription = null, tint = if(navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile")Red else Color.Black) }, label = { androidx.compose.material3.Text(
            text = "My Profile",
            fontSize = 10.sp,
            color = if(navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile")Red else Color.Black,
        )
        }, selectedContentColor =if(navController.currentBackStackEntryAsState().value?.destination?.route == "myProfile")Red else Color.Black)
    }
}

@Composable
fun MainTopBar(navController: NavController, text: String, back: Boolean = true,) {
    TopAppBar(backgroundColor = Red, elevation = 0.dp) {

        Box(modifier = Modifier.fillMaxSize()) {
            if(back) {
                IconButton(onClick = { navController.navigateUp() }) {
                    androidx.compose.material.Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back", tint = Color.White, modifier = Modifier
                        .size(30.dp)
                        .align(
                            Alignment.CenterStart
                        ))
                }
            }

            Text(text = text, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                Alignment.Center ))
        }



    }
}