package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.models.GetAllOrderResponseItem
import com.angad.mediadmin.navigation.Routes
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun OrderScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val context = LocalContext.current
    val state = viewModel.getAllOrders.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllOrders()
    }

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
            Toast.makeText(context,  state.value.error, Toast.LENGTH_SHORT).show()
        }

        state.value.data != null -> {
            val data = state.value.data
//            Toast.makeText(context, "Data fetch successfully", Toast.LENGTH_SHORT).show()
//            Log.d("TAG", "OrderScreen: $data")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(data!!){
                        ShowOrdersCard(res = it, navController = navController)
                    }
                }
            }
        }
    }

}

@Composable
fun ShowOrdersCard(res: GetAllOrderResponseItem, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate(Routes.ApproveOrderRoutes(orderId = res.order_id))
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
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            //    For product icon
            Column {
                Card(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Product Icon")
                }
            }

            //    For product name
            Column {
                Text(text = res.product_name)
                Text(text = "Category: ${res.category}")
                Text(text = "Price: ${res.product_price}")
                Text(text = "Quantity: ${res.quantity}")
                Text(text = "Data of Order: ${res.date_of_order_creation}")
            }
        }
    }
}
