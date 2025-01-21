package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.models.GetAllUsersResponseItem
import com.angad.mediadmin.navigation.routes.Routes
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun AllUsersScreen( navController: NavController, viewModels: MyViewModel = hiltViewModel()) {

//    Creating a context
    val context = LocalContext.current
    val state = viewModels.getAllUsers.collectAsState()

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
            Toast.makeText(context, "Data fetch successfully", Toast.LENGTH_SHORT).show()
            val users = state.value.data

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)

            ) {
                 Row( modifier = Modifier.fillMaxWidth(),
                     verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.Center
                 ) {
                      Image(
                          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                          contentDescription = "Back Arrow",
                          modifier = Modifier.clickable {
                              navController.popBackStack()
                          }
                      )

                     Text (
                         text = "All Users",
                         style = TextStyle(
                             fontSize = 24.sp,
                             fontWeight = FontWeight.Medium
                         ),
                         textAlign = TextAlign.Center,
                         modifier = Modifier.fillMaxWidth()
                     )
                 }
                
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(users!!){
                        ShowUsersCard(res = it, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUsersCard(res: GetAllUsersResponseItem, navController: NavController) {

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(
                    Routes.UserDetailsRoutes(
                        address = res.address,
                        approved = res.isApproved,
                        block = res.block,
                        date_of_account_creation = res.date_of_account_creation,
                        email = res.email,
                        id = res.id,
                        name = res.name,
                        password = res.password,
                        phone = res.phone_number,
                        pinCode = res.pin_code,
                        user_id = res.user_id,
                    )
                )
            }
            .fillMaxSize()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = Color.Black
            ),
        colors = CardDefaults.cardColors(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Account Status",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding( end = 4.dp)
                    )
                    StatusIcon(color = Color.Green)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Block Status",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        ),

                        modifier = Modifier.padding(end = 4.dp)
                    )
                    StatusIcon(color = Color.Red)   //211
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = res.name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    ),
                )
                
                Text(
                    text = res.phone_number,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = res.email,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Text(
                    text = res.date_of_account_creation,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun StatusIcon(color: Color) {
    Card(
        modifier = Modifier.size(5.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {

    }

}