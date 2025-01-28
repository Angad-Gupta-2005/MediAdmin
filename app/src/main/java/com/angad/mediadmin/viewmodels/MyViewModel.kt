package com.angad.mediadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.GetSpecificUser
import com.angad.mediadmin.models.UserModels
import com.angad.mediadmin.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor( private val repo: Repo) : ViewModel() {

//    Mutable state flow for get all users
    private val _getAllUsers  = MutableStateFlow(GetAllUsersState())
    val getAllUsers = _getAllUsers.asStateFlow()

//    Mutable state flow for get specific user
    private val _getSpecificUser  = MutableStateFlow(GetSpecificUserState())
    val getSpecificUser = _getSpecificUser.asStateFlow()


//      Function that fetch all users details
    fun getAllUsers(){

        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllUsers().collect{
                when(it){
                    is Results.Loading -> {
                        _getAllUsers.value = GetAllUsersState( isLoading = true )
                    }
                    is Results.Error -> {
                        _getAllUsers.value = GetAllUsersState( error = it.message, isLoading = false )
                    }
                    is Results.Success -> {
                        _getAllUsers.value = GetAllUsersState( data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }

//    Function that fetch specific user details
    fun getSpecificUser(userID: String){

        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUser(userID).collect{
                when(it){
                    is Results.Loading -> {
                        _getSpecificUser.value = GetSpecificUserState( isLoading = true )
                    }

                    is Results.Error -> {
                        _getSpecificUser.value = GetSpecificUserState( error = it.message, isLoading = false )
                    }

                    is Results.Success -> {
                        _getSpecificUser.value = GetSpecificUserState( data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }



}

//   GetAllUsers state
data class GetAllUsersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: UserModels? = null
)

//   GetSpecificUser state
data class GetSpecificUserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: GetSpecificUser? = null
)