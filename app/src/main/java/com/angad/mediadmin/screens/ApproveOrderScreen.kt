package com.angad.mediadmin.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

    LaunchedEffect(Unit) {
        viewModel.getSpecificOrder(orderId)
    }

    val deleteState = viewModel.deleteOrder.collectAsState()
    when {
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
            Log.d("TAG", "ApproveOrderScreen: Delete successful")
            Toast.makeText(context, deleteState.value.data!!.message, Toast.LENGTH_SHORT).show()
            deleteState.value.data = null
            navController.navigateUp()
        }
    }

    when {
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
            var isApproved by remember { mutableIntStateOf(order.isApproved) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Scrollable content
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Order Approval Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Order Status:",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = if (isApproved == 1) "✅ Approved" else "⏳ Pending",
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = if (isApproved == 1) Color.Green else Color.Red
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                        //    For approved the user
                            Switch(
                                checked = isApproved == 1,
                                onCheckedChange = { isChecked ->
                                    isApproved = if (isChecked) 1 else 0
                                    viewModel.approveOrder(orderId, isApproved)

                                    Toast.makeText(
                                        context,
                                        if (isApproved == 1) "Order Approved" else "Order Pending",
                                        Toast.LENGTH_SHORT
                                    ).show()
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

                        // Delete Order Button
                        Button(
                            onClick = { viewModel.deleteOrder(orderId) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(text = "🗑 Delete Order", color = Color.White)
                        }
                    }
                }

                // Order Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Order Details",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OrderDetailRow("📦 Product Name:", order.product_name)
                        OrderDetailRow("💰 Product Price:", "₹${order.product_price}")
                        OrderDetailRow("🔢 Quantity:", order.quantity.toString())
                        OrderDetailRow("👤 User Name:", order.user_name)
                        OrderDetailRow("📞 Phone Number:", order.phone_number)
                        OrderDetailRow("🏠 Address:", order.address)
                        OrderDetailRow("🗓 Order Date:", order.date_of_order_creation)
                        OrderDetailRow("🛒 Category:", order.category)
                        OrderDetailRow("✉️ Message:", order.message)
                    }
                }
            }
        }
    }
}

// 🔹 Reusable Row for Order Details
@Composable
fun OrderDetailRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
            color = Color.DarkGray
        )
    }
}