package com.angad.mediadmin.models

data class GetAllOrderResponseItem(
    val address: String,
    val category: String,
    val date_of_order_creation: String,
    val id: Int,
    val isApproved: Int,
    val message: String,
    val order_id: String,
    val phone_number: String,
    val product_id: String,
    val product_name: String,
    val product_price: Int,
    val quantity: Int,
    val total_price: Int,
    val user_id: String,
    val user_name: String
)