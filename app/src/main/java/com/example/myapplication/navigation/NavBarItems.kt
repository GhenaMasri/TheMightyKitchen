package com.example.myapplication.navigation

import com.example.myapplication.R

enum class NavBarItems(
    val label: Int,
    val icon: Int,
    val route: String,
) {
    ORDERS(
        label = R.string.orders,
        icon = R.drawable.order,
        route = Screens.Orders.name
    ),
    CHAT(
        label = R.string.chat,
        icon = R.drawable.chat,
        route = Screens.Chat.name
    ),

}