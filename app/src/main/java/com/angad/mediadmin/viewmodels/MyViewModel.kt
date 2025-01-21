package com.angad.mediadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.GetAllUsersResponse
import com.angad.mediadmin.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor( private val repo: Repo) : ViewModel() {

    private val _getAllUsers  = MutableStateFlow(GetAllUsersState())
    val getAllUsers = _getAllUsers.asStateFlow()

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
}

//   GetAllUsers state
data class GetAllUsersState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: GetAllUsersResponse? = null
)