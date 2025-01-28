package com.angad.mediadmin.screens

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun UserDetails(
    id: String? = null,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val state = viewModel.getSpecificUser.collectAsState()

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getSpecificUser(id)
        }
    }

//    Handling the state
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
            var isChecked by remember { mutableStateOf(data.isApproved == 2) }

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
                                text = "Block User",
                                color = Color.Red
                            )
                            
                        //    Creating a switch
                            Switch(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    isChecked = checked
                                    if (isChecked) {
                                        viewModel.getSpecificUser(id!!)
                                    } else {
                                        viewModel.getSpecificUser(id!!)
                                    }

                                }
                            )
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))

                    //    Creating a button that delete the user
                        Button(
                            modifier = Modifier.padding(10.dp),
                            onClick = {
                                if (id != null){
                                    //    Delete the user
                                }
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

