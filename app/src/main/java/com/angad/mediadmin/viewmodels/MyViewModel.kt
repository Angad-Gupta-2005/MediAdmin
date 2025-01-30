package com.angad.mediadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.DeleteSpecificUserResponse
import com.angad.mediadmin.models.GetSpecificUser
import com.angad.mediadmin.models.UpdateUserDetailsResponse
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

//    Mutable state flow for delete user
    private val _deleteSpecificUser  = MutableStateFlow(DeleteSpecificUserState())
    val deleteSpecificUser = _deleteSpecificUser.asStateFlow()

//    Mutable state flow for edit user
    private val _updateUserInfo  = MutableStateFlow(UpdateUserDetailsState())
    val updateUserInfo = _updateUserInfo.asStateFlow()


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

//    Function that update specific user details
    fun updateUserInfo(
        user_id: String? = null,
        name: String? = null,
        email: String? = null,
        password: String? = null,
        address: String? = null,
        pin_code: String? = null,
        phone_number: String? = null,
        isApproved: Int? = null,
        date_of_account_creation: String? = null,
        block: Int? = null
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateUserInfo(
                user_id = user_id,
                name = name,
                email = email,
                password = password,
                address = address,
                pin_code = pin_code,
                phone_number = phone_number,
                isApproved = isApproved,
                date_of_account_creation = date_of_account_creation,
                block = block
            ).collect{
                when(it){
                    is Results.Loading -> {
                        _updateUserInfo.value = UpdateUserDetailsState( isLoading = true)
                    }

                    is Results.Error -> {
                        _updateUserInfo.value = UpdateUserDetailsState( error = it.message, isLoading = false)
                    }

                    is Results.Success -> {
                        _updateUserInfo.value = UpdateUserDetailsState( data = it.data.body(), isLoading = false)
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

//      Update user details state
data class UpdateUserDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: UpdateUserDetailsResponse? = null
)

//    Delete specific user state
data class DeleteSpecificUserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: DeleteSpecificUserResponse? = null
)