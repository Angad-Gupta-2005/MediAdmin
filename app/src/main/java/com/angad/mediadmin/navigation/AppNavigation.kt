package com.angad.mediadmin.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angad.mediadmin.screens.AddProduct
import com.angad.mediadmin.screens.AllProductsScreen
import com.angad.mediadmin.screens.AllUsersScreen
import com.angad.mediadmin.screens.ApproveOrderScreen
import com.angad.mediadmin.screens.BottomNav
import com.angad.mediadmin.screens.DashboardScreen
import com.angad.mediadmin.screens.EditUserUI
import com.angad.mediadmin.screens.OrderScreen
import com.angad.mediadmin.screens.UserDetails
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun AppNavigation (viewModel: MyViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.BottomNavRoutes) {

        composable<Routes.AllUsersUIRoutes>{
            AllUsersScreen(navController = navController)
        }

        composable<Routes.UserDetailsRoutes>{ backStack ->

            val userId: String = backStack.arguments?.getString("user_id") ?: ""
            
            UserDetails(id = userId, viewModel = viewModel, navController = navController)

        }

        composable<Routes.EditUserScreen>{ backStack ->

            val userId: String = backStack.arguments?.getString("user_id") ?: ""

            EditUserUI(id = userId, viewModel = viewModel, navController = navController)

        }

        composable<Routes.AddProductRoutes> {
            AddProduct(navController = navController)
        }

    //    For bottom navigation
        composable<Routes.BottomNavRoutes> {
            BottomNav(navController = navController)
        }

    //    For dashboard screen
        composable<Routes.DashboardRoutes> {
            DashboardScreen()
        }

    //    For order screen
        composable<Routes.OrderScreenRoutes> {
            OrderScreen(navController = navController)
        }

    //    For all products screen
        composable<Routes.AllProductsRoutes> {
            AllProductsScreen(navController = navController)
        }

    //    For approved order screen
        composable<Routes.ApproveOrderRoutes> { backStack ->
            val orderId: String = backStack.arguments?.getString("orderId") ?: ""
            ApproveOrderScreen(orderId = orderId,navController = navController, viewModel = viewModel)
        }
    }

}