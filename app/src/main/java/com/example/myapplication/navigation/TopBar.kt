package com.example.myapplication.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, modifier: Modifier = Modifier) {
   val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = getTitle(destination = currentDestination))
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    )
}

@Composable
fun getTitle(destination : NavDestination?): String{
    return when(destination?.route){
        Screens.Orders.name -> stringResource(id = R.string.orders)
        Screens.Chat.name -> stringResource(id = R.string.chat)
        else -> stringResource(id = R.string.app_name)
    }
}