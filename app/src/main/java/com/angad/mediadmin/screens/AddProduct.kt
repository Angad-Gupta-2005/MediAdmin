package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.viewmodels.MyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct( viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val productName = remember {
        mutableStateOf("")
    }

    val productPrice = remember {
        mutableStateOf("")
    }

    val productCategory = remember {
        mutableStateOf("")
    }

    val productStock = remember {
        mutableStateOf("")
    }

//    Handle the add product state
    val context = LocalContext.current
    val state = viewModel.addProduct.collectAsState()
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
            Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
        }
    }


//    Layout designing start here
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "Add New Products",
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Product Details",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(top = 20.dp),
                color = Color(0xFF358BE0)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = productName.value,
                onValueChange = { productName.value = it },
                label = { Text(text = "Product Name", fontWeight = FontWeight.SemiBold, color = Color(0xFF1976D2))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors( Color(0xFF66A9EC))
            )

            Spacer(modifier = Modifier.height(10.dp))

            //    For product price input
            OutlinedTextField(
                value = productPrice.value,
                onValueChange = { productPrice.value = it },
                label = { Text(text = "Product Price", fontWeight = FontWeight.SemiBold, color = Color(0xFF1976D2))},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors( Color(0xFF66A9EC))
            )

            Spacer(modifier = Modifier.height(10.dp))

            //    For product category input
            OutlinedTextField(
                value = productCategory.value,
                onValueChange = { productCategory.value = it },
                label = { Text(text = "Product Category", fontWeight = FontWeight.SemiBold, color = Color(0xFF1976D2))},
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors( Color(0xFF66A9EC))
            )

            Spacer(modifier = Modifier.height(10.dp))

            //    For product stock input
            OutlinedTextField(
                value = productStock.value,
                onValueChange = { productStock.value = it },
                label = { Text(text = "Product Stock", fontWeight = FontWeight.SemiBold, color = Color(0xFF1976D2))},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors( Color(0xFF66A9EC))
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (productName.value.isNotEmpty() && productPrice.value.isNotEmpty() &&
                        productCategory.value.isNotEmpty() && productStock.value.isNotEmpty()
                    ){
                        viewModel.addProduct(
                            productName = productName.value,
                            productPrice = productPrice.value.toFloat(),
                            productCategory = productCategory.value,
                            productStock = productStock.value.toInt()
                        )
                    } else {
                        Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.padding(10.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF66A9EC),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    text = "Add Product to Store",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }


}