package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.R
import com.angad.mediadmin.models.UserModelsItem
import com.angad.mediadmin.navigation.Routes
import com.angad.mediadmin.viewmodels.MyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllUsersScreen( navController: NavController, viewModels: MyViewModel = hiltViewModel()) {

//    Creating a context
    val context = LocalContext.current
    val state = viewModels.getAllUsers.collectAsState()

//    For horizontal pager
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeMenu.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    LaunchedEffect(Unit) {
        viewModels.getAllUsers()
    }

    when {
    //    If the data is loading
        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    //    If there are some error
        state.value.error != null -> {
            Toast.makeText(context, state.value.error, Toast.LENGTH_LONG).show()
        }
    //    If it success then fetch the data
        state.value.data != null -> {
           // Toast.makeText(context, "Data fetch successfully", Toast.LENGTH_SHORT).show()
            val users = state.value.data!!
           // state.value.data = null

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(
                            text = "All Users",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.SansSerif
                        ) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF66A9EC),
                            titleContentColor = Color.White
                        )
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE3F2FD))
                        .padding(innerPadding)
                ) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex.value,
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = Color(0xFF66A9EC)
                    ){
                        HomeMenu.entries.forEachIndexed { index, currentTab ->
                            val tabColor = if (index == selectedTabIndex.value) Color.White else Color.LightGray
                            val fontWeight = if (index == selectedTabIndex.value) FontWeight.Bold else FontWeight.Normal

                            Tab(
                                selected = index == selectedTabIndex.value,
                                selectedContentColor = MaterialTheme.colorScheme.primary,
                                unselectedContentColor = MaterialTheme.colorScheme.outline,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(currentTab.ordinal)
                                    }
                                }
                            ){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = if (index == selectedTabIndex.value) currentTab.selectedIcon else currentTab.unselectedIcon,
                                        contentDescription = currentTab.title,
                                        tint = tabColor
                                    )
                                    Spacer(modifier = Modifier.width(8.dp)) // Add space between icon and text
                                    Text(
                                        text = currentTab.title,
                                        color = tabColor,
                                        fontWeight = fontWeight
                                    )
                                }
                            }
                        }
                    }


                //    Horizontal pager
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) { page ->
                        val filteredUsers = when (page) {
                            HomeMenu.ApprovedUser.ordinal -> users.filter { it.isApproved == 1 }
                            HomeMenu.PendingUser.ordinal -> users.filter { it.isApproved == 0 }
                            else -> emptyList()
                        }

                    //    Using a Lazy column to show the user
                        if (filteredUsers.isEmpty()){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No users available", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(filteredUsers) { user ->
                                    UserCard(user = user, navController = navController)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun UserCard(user: UserModelsItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Routes.UserDetailsRoutes(user.user_id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
        //    For user image icons
            Column {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Product Icon",
                    modifier = Modifier
                        .padding(12.dp)
                        .size(70.dp),
                    alignment = Alignment.Center
                )
            }

        //    For user content
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Text(text = "Name: ${user.name}")
                Text(text = "Email: ${user.email}")
                Text(
                    text = if (user.isApproved == 1) "Status: Approved" else "Status: Pending",
                    color = if (user.isApproved == 1) Color(0xFF0DB60D) else Color.Red
                )
            }

        }
    }
}


enum class HomeMenu(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String
){
    ApprovedUser(
        selectedIcon = Icons.Filled.Check,
        unselectedIcon = Icons.Outlined.Check,
        title = "Approved User"
    ),

    PendingUser(
        selectedIcon = Icons.Filled.Warning,
        unselectedIcon = Icons.Outlined.Warning,
        title = "Pending User"
    )
}
