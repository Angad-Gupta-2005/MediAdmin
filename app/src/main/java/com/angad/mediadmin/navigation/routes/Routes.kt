package com.angad.mediadmin.navigation.routes

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object AllUsersUIRoutes

    @Serializable
    data class UserDetailsRoutes(
        val address: String,
        val approved: Int,
        val block: Int,
        val date_of_account_creation: String,
        val email: String,
        val id: Int,
        val name: String,
        val password: String,
        val phone: String,
        val pinCode: String,
        val user_id: String,
    )

}
