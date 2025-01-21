package com.angad.mediadmin.navigation.appnavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.angad.mediadmin.navigation.routes.Routes
import com.angad.mediadmin.screens.AllUsersScreen
import com.angad.mediadmin.screens.UserDetails
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun AppNavigation (viewModel: MyViewModel = hiltViewModel()) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.AllUsersUIRoutes) {

        composable<Routes.AllUsersUIRoutes>{
            AllUsersScreen(navController = navController)
        }

        composable<Routes.UserDetailsRoutes>{ backStack ->

        //    For back stack entry
            val data: Routes.UserDetailsRoutes = backStack.toRoute()
            UserDetails(
                res = data,
                viewModel = viewModel,
                navController = navController
            )

        }
    }

}