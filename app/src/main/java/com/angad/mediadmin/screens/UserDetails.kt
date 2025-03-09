package com.angad.mediadmin.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.navigation.Routes
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun UserDetails(
    userId: String,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val state = viewModel.getSpecificUser.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSpecificUser(userId)
    }

//    **** Work in future *****

//    Handling the state of block user or approved user
    val approvedState = viewModel.approveUser.collectAsState()

    when{
        approvedState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        approvedState.value.error != null -> {
            Toast.makeText(context, approvedState.value.error, Toast.LENGTH_SHORT).show()
        }

        approvedState.value.data != null -> {
            val data = approvedState.value.data!!
           // Toast.makeText(context, data.message , Toast.LENGTH_SHORT).show()
            approvedState.value.data = null
        }
    }


//    Handling the state of delete user
    val deleteState = viewModel.deleteSpecificUser.collectAsState()
    when{
        deleteState.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        deleteState.value.error != null -> {
            Toast.makeText(context, deleteState.value.error, Toast.LENGTH_SHORT).show()
        }

        deleteState.value.data != null -> {
            val data = deleteState.value.data!!
            Toast.makeText(context,  data.message, Toast.LENGTH_SHORT).show()
            deleteState.value.data = null
        }
    }


//    Handling the state of getSpecific user
    when{

        state.value.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
        }


        state.value.data != null -> {
            val data = state.value.data!!
            Log.d("isApproved", "UserDetails: ${data.isApproved}")
            var isApproved by remember { mutableIntStateOf(data.isApproved) }

            Log.d("isApproved", "UserDetails: ${data.isApproved}")
            val userDetails = listOf(
                "Name:" to data.name,
                "User ID:" to data.user_id,
                "Email:" to data.email,
                "Phone Number:" to data.phone_number,
                "Address:" to data.address,
                "Password:" to data.password,
                "Pin Code:" to data.pin_code,
                "Account Created On:" to data.date_of_account_creation,
                "Status:" to if (data.isApproved == 1) "Approved" else if (data.isApproved == 0) "Approval Pending" else "Blocked"
            )

//            Design the first column

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column( modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "User Status:",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isApproved == 1) "✅ Approved" else "⏳ Pending",
                                fontWeight = FontWeight.Bold,
                                color = if (isApproved == 1) Color(0xFF08A508) else Color.Red
                            )
                            
                        //    Creating a switch
                            Switch(
                                checked = isApproved == 1,
                                onCheckedChange = { isChecked ->

                                    isApproved = if (isChecked) 1 else 0

                                    viewModel.approveUser(user_id = userId, isApproved = isApproved)
                                    Toast.makeText(
                                        context,
                                        if (isApproved == 1) "User Approved" else "Approval is pending",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))

                    //    Creating a button that delete the user
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = {
                                viewModel.deleteSpecificUser(userId)
                                navController.navigateUp()
                            }) {
                            Text(text = "Delete User")
                        }
                    }
                }

                //    Second card start here
                Card(
                    modifier = Modifier
                        .height(500.dp)
                        .padding(10.dp)
                ) {

                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(100.dp)
                        )
                    }

                //
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userDetails){ details ->
                            ItemView(label = details.first, value = details.second)
                        }
                    }
                }
                
            //    Creating a button that edit user profile
                Button(
                    onClick = {
                        navController.navigate(
                            Routes.EditUserScreen(user_id = data.user_id)
                        )
                    }
                ) {
                    Text(text = "Edit User Profile")
                }
            }
        }

    }

}

@Composable
fun ItemView(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
    //    For space
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value)
    }
}

