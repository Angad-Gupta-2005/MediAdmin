package com.angad.mediadmin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.AddProductResponse
import com.angad.mediadmin.models.ApprovedOrderResponse
import com.angad.mediadmin.models.DeleteOrderResponse
import com.angad.mediadmin.models.DeleteSpecificUserResponse
import com.angad.mediadmin.models.GetAllOrderResponse
import com.angad.mediadmin.models.GetAllProductsResponse
import com.angad.mediadmin.models.GetSpecificOrder
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

//    Mutable state flow for approve user
    private val _approveUser  = MutableStateFlow(UpdateUserDetailsState())
    val approveUser = _approveUser.asStateFlow()

//    Mutable state flow for add product
    private val _addProduct = MutableStateFlow(AddProductState())
    val addProduct = _addProduct.asStateFlow()

//    Mutable state flow for get all products
    private val _getAllProducts = MutableStateFlow(GetAllProductsState())
    val getAllProducts = _getAllProducts.asStateFlow()

//    Mutable state flow for get all orders
    private val _getAllOrders = MutableStateFlow(GetAllOrdersState())
    val getAllOrders = _getAllOrders.asStateFlow()

//    Mutable state flow for get specific order
    private val _getSpecificOrder = MutableStateFlow(GetSpecificOrderState())
    val getSpecificOrder = _getSpecificOrder.asStateFlow()

//    Mutable state flow for approve order
    private val _approvedOrder = MutableStateFlow(ApprovedOrderState())
    val approvedOrder = _approvedOrder.asStateFlow()

//    Mutable state flow for delete order
    private val _deleteOrder = MutableStateFlow(DeleteOrderState())
    val deleteOrder = _deleteOrder.asStateFlow()


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


//    Function that approve user by admin
    fun approveUser(user_id: String, isApproved: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.approveUser(user_id, isApproved).collect{
                when(it){
                    is Results.Loading -> {
                        _approveUser.value = UpdateUserDetailsState( isLoading = true)
                    }

                    is Results.Error -> {
                        _approveUser.value = UpdateUserDetailsState( error = it.message, isLoading = false)
                    }

                    is Results.Success -> {
                        _approveUser.value = UpdateUserDetailsState( data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }


//    Function that delete specific user
    fun deleteSpecificUser(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSpecificUser(id).collect{
                when(it){
                    is Results.Loading -> {
                        _deleteSpecificUser.value = DeleteSpecificUserState( isLoading = true )
                    }

                    is Results.Error -> {
                        _deleteSpecificUser.value = DeleteSpecificUserState( error = it.message, isLoading = false )
                    }

                    is Results.Success -> {
                        _deleteSpecificUser.value = DeleteSpecificUserState( data = it.data.body(), isLoading = false)
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


//    Function that add product
    fun addProduct(
        productName: String?,
        productPrice: Float?,
        productCategory: String?,
        productStock: Int?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addProduct(
                productName = productName,
                productPrice = productPrice,
                productCategory = productCategory,
                productStock = productStock
            ).collect{
                when(it){
                    is Results.Loading -> {
                        _addProduct.value = AddProductState(isLoading = true)
                    }

                    is Results.Error -> {
                        _addProduct.value = AddProductState(error = it.message, isLoading = false)
                    }

                    is Results.Success -> {
                        _addProduct.value = AddProductState(data = it.data.body(), isLoading = false)
                    }
                }
            }
        }
    }

//    Function that fetch all products details
    fun getAllProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllProducts().collect{
                when(it){
                    is Results.Loading -> {
                        _getAllProducts.value = GetAllProductsState( isLoading = true )
                    }

                    is Results.Error -> {
                        _getAllProducts.value = GetAllProductsState( error = it.message, isLoading = false )
                    }

                    is Results.Success -> {
                        _getAllProducts.value = GetAllProductsState( data = it.data.body(), isLoading = false)
                    }
                }
            }

        }
    }

//    Function that fetch all orders details
    fun getAllOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllOrders().collect {
                when(it){
                    is Results.Loading -> {
                        _getAllOrders.value = GetAllOrdersState(loading = true)
                    }
                    is Results.Error -> {
                        _getAllOrders.value = GetAllOrdersState(error = it.message, loading = false)
                    }
                    is Results.Success -> {
                        _getAllOrders.value = GetAllOrdersState(data = it.data.body(), loading = false)
                    }
                }
            }
        }
    }

//    Function that fetch specific order details
    fun getSpecificOrder(orderId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificOrder(orderId).collect{
                when(it){
                    is Results.Loading -> {
                        _getSpecificOrder.value = GetSpecificOrderState(loading = true)
                    }

                    is Results.Error -> {
                        _getSpecificOrder.value = GetSpecificOrderState( error = it.message, loading = false)
                    }

                    is Results.Success -> {
                        _getSpecificOrder.value = GetSpecificOrderState( data = it.data.body(), loading = false)
                    }
                }
            }
        }
    }

//    Function that approved the order
    fun approveOrder(orderId: String, isApproved: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repo.approveOrder(orderId, isApproved).collect{
                when(it){
                    is Results.Loading -> {
                        _approvedOrder.value = ApprovedOrderState(loading = true)
                    }

                    is Results.Error -> {
                        _approvedOrder.value = ApprovedOrderState(error = it.message, loading = false)
                    }

                    is Results.Success -> {
                        _approvedOrder.value = ApprovedOrderState(data = it.data.body(), loading = false)
                    }
                }
            }
        }
    }


//    Function that delete the specific order
    fun deleteOrder(orderId: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSpecificOrder(orderId).collect{
                when(it){
                    is Results.Loading -> {
                        _deleteOrder.value = DeleteOrderState(loading = true)
                    }

                    is Results.Error -> {
                        _deleteOrder.value = DeleteOrderState(error = it.message, loading = false)
                    }

                    is Results.Success -> {
                        _deleteOrder.value = DeleteOrderState(data = it.data.body(), loading = false)
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
    var data: UserModels? = null
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
    var data: UpdateUserDetailsResponse? = null
)

//    Delete specific user state
data class DeleteSpecificUserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    var data: DeleteSpecificUserResponse? = null
)

//    Add Product state
data class AddProductState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: AddProductResponse? = null
)

//    Get all products state
data class GetAllProductsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: GetAllProductsResponse? = null
)

//    Get all order state
data class GetAllOrdersState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: GetAllOrderResponse? = null
)

//    Get specific order state
data class GetSpecificOrderState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: GetSpecificOrder? = null
)

//    Approved order state
data class ApprovedOrderState(
    val loading: Boolean = false,
    val error: String? = null,
    val data: ApprovedOrderResponse? = null
)

//    delete order state
data class DeleteOrderState(
    val loading: Boolean = false,
    val error: String? = null,
    var data: DeleteOrderResponse? = null
)