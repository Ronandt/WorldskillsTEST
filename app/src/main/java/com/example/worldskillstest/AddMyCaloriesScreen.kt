package com.example.worldskillstest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMyCaloriesScreen(navController: NavController)   {
    androidx.compose.material.Scaffold(bottomBar = {
      NavBar(navController = navController)
    }) {

            Box(modifier = Modifier.padding(it).fillMaxSize()) {
                Text("Intentionally left blank", modifier = Modifier.align(Alignment.Center))
            }

    }
}