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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun ApproveOrderScreen(orderId: String, navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val state = viewModel.getSpecificOrder.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getSpecificOrder(orderId)
    }

//    Manage the delete order state
    val deleteState = viewModel.deleteOrder.collectAsState()
    when{
        deleteState.value.loading -> {
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
            Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
        }
    }

//    Manage the getSpecificOrder state
    when{
        state.value.loading -> {
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
            val order = state.value.data!!
            var isApproved by remember { mutableStateOf(order.isApproved) }

        //    Design the UI
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(text = if (isApproved == 1) "Approved" else "Pending")

                            Switch(
                                checked = isApproved == 1,
                                onCheckedChange = { isChecked ->
                                    isApproved = if (isChecked) 1 else 0
                                    viewModel.approveOrder(orderId, isApproved)

                                    if (isApproved == 1){
                                        Toast.makeText(context, "Order Approved", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.Green,
                                    uncheckedThumbColor = Color.Red,
                                    checkedTrackColor = Color.LightGray,
                                    uncheckedTrackColor = Color.DarkGray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                    //    Creating a button for delete the order
                        Button(
                            onClick = {
                                viewModel.deleteOrder(orderId)
                                navController.navigateUp()
                            }) {
                            Text(text = "Delete Order")
                        }
                    }
                }

            //    Second card start here
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Text(text = "Product Name: ${order.product_name}")
                        Text(text = "Product Price: ${order.product_price}")
                        Text(text = "Product Quantity: ${order.quantity}")
                        Text(text = "User Name: ${order.user_name}")
                        Text(text = "User Phone Number: ${order.phone_number}")
                        Text(text = "User Address: ${order.address}")
                        Text(text = "Order Status: ${if (order.isApproved == 1) "Approved" else "Pending"}")
                        Text(text = "Order Date: ${order.date_of_order_creation}")
                        Text(text = "Category: ${order.category}")
                        Text(text = "Message: ${order.message}")

                        Log.d("TAG", "ApproveOrderScreen: text")
                    }
                }

            }

        }
    }
}