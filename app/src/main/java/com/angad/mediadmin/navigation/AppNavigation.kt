package com.angad.mediadmin.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.angad.mediadmin.screens.AddProduct
import com.angad.mediadmin.screens.AllUsersScreen
import com.angad.mediadmin.screens.EditUserUI
import com.angad.mediadmin.screens.UserDetails
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun AppNavigation (viewModel: MyViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.AddProductRoutes) {

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
    }

}