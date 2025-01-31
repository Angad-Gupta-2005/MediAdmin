package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.viewmodels.MyViewModel


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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Product")

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            label = { Text(text = "Product Name")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

    //    For product price input
        OutlinedTextField(
            value = productPrice.value,
            onValueChange = { productPrice.value = it },
            label = { Text(text = "Product Price")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

    //    For product category input
        OutlinedTextField(
            value = productCategory.value,
            onValueChange = { productCategory.value = it },
            label = { Text(text = "Product Category")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

    //    For product stock input
        OutlinedTextField(
            value = productStock.value,
            onValueChange = { productStock.value = it },
            label = { Text(text = "Product Stock")},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                if (productName.value.isNotEmpty() || productPrice.value.isNotEmpty() ||
                    productCategory.value.isNotEmpty() || productStock.value.isNotEmpty()
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
            }
        ) {
            Text(text = "Add Product to Store")
        }
    }
}