package com.angad.mediadmin.models

data class UserModelsItem(
    val address: String,
    val block: Int,
    val date_of_account_creation: String,
    val email: String,
    val id: Int,
    val isApproved: Int,
    val name: String,
    val password: String,
    val phone_number: String,
    val pin_code: String,
    val user_id: String
)