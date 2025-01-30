package com.angad.mediadmin.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.angad.mediadmin.viewmodels.MyViewModel

@Composable
fun EditUserUI(
    id: String? = null,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val context = LocalContext.current

    val state = viewModel.updateUserInfo.collectAsState()

//    Handling the state of update the user
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
            state.value.data = null
        }
    }

    
    
    
    
//    Taking the user input
    val userName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val address = remember {
        mutableStateOf("")
    }

    val pinCode = remember {
        mutableStateOf("")
    }

    val phoneNumber = remember {
        mutableStateOf("")
    }






//    Design user screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
    //    Creating a text
        Text(text = "Edit Angad Gupta's Details")
        
        Spacer(modifier = Modifier.height(20.dp))
        
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text(text = "Name") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text(text = "Email")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = {password.value = it},
            label = { Text(text = "Password")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text(text = "Address")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = pinCode.value,
            onValueChange = { pinCode.value = it },
            label = { Text(text = "Pin Code")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            label = { Text(text = "Phone Number")},
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))
        
        Button(
            onClick = {
                if ( userName.value.isNotEmpty() || email.value.isNotEmpty() ||
                    password.value.isNotEmpty() || address.value.isNotEmpty() ||
                    pinCode.value.isNotEmpty() || phoneNumber.value.isNotEmpty()){
                //    Calling the function that update the user details
                    viewModel.updateUserInfo(
                        user_id = id,
                        name = userName.value,
                        email = email.value,
                        password = password.value,
                        address = address.value,
                        pin_code = pinCode.value,
                        phone_number = phoneNumber.value
                    )
                //    Finish the screen
                    navController.navigateUp()
                } else {
                    Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Update Details")
        }

    }
    

}