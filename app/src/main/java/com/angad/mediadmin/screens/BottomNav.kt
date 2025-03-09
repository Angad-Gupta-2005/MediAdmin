package com.angad.mediadmin.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.angad.mediadmin.models.BottomNavItem

@Composable
fun BottomNav(navController: NavController) {

    var selectedIndex by remember { mutableIntStateOf(0) }

    val bottomItem = listOf(
        BottomNavItem("Dashboard", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Order", Icons.Filled.AddCircle, Icons.Outlined.AddCircle),
        BottomNavItem("Product", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        BottomNavItem("Customers", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                bottomItem.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedIndex == index) item.icon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        }
                    )

                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), navController = navController, selectedIndex = selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier, navController: NavController, selectedIndex: Int) {
    when(selectedIndex){

        0 -> DashboardScreen()
        1 -> OrderScreen(navController = navController)
        2 -> AllProductsScreen(navController = navController)
        3 -> AllUsersScreen(navController = navController)

//        0 -> navController.navigate(Routes.DashboardRoutes)
//        1 -> navController.navigate(Routes.OrderScreenRoutes)
//        2 -> navController.navigate(Routes.AddProductRoutes)
//        3 -> navController.navigate(Routes.AllUsersUIRoutes)

    }
}
