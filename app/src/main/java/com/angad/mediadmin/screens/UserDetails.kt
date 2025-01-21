package com.angad.mediadmin.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.navigation.routes.Routes
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun UserDetails(res: Routes.UserDetailsRoutes, viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val context = LocalContext.current
}