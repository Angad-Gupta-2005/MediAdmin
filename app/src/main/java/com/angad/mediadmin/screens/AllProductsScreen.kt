package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.angad.mediadmin.models.GetAllProductsResponseItem
import com.angad.mediadmin.navigation.Routes
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun AllProductsScreen(navController: NavController, viewModel: MyViewModel = hiltViewModel() ) {

    val context = LocalContext.current
    val state = viewModel.getAllProducts.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllProducts()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                    navController.navigate(Routes.AddProductRoutes)
                },
                modifier = Modifier.padding(bottom = 45.dp, end = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ){ innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
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
                    val data = state.value.data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ){
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(data!!){
                                ShowProductsCard(res = it, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowProductsCard(res: GetAllProductsResponseItem, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
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
                Text(text = res.name)
                Text(text = "Category: ${res.category}")
                Text(text = "Price: ${res.price}")
                Text(text = "Stock: ${res.stock}")
            }
        }

    }
}
