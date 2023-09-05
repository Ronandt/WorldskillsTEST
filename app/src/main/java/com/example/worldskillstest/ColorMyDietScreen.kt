package com.example.worldskillstest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ColorMyDietScreen(navController: NavController) {
    androidx.compose.material.Scaffold(bottomBar = {
        NavBar(navController = navController)
    }) {
        Box(modifier = Modifier.padding(it).fillMaxSize()) {
            Text("Intentionally left blank",modifier = Modifier.align(Alignment.Center))
        }
    }
}