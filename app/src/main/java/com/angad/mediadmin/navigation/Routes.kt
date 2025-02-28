package com.angad.mediadmin.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object AllUsersUIRoutes

    @Serializable
    data class UserDetailsRoutes(
        val user_id: String
    )

    @Serializable
    data class EditUserScreen( val user_id: String )

    @Serializable
    object AddProductRoutes

//    For bottom navigation
    @Serializable
    object BottomNavRoutes

//    For Dashboard screen
    @Serializable
    object DashboardRoutes

//    For order screen
    @Serializable
    object OrderScreenRoutes

//    For get all products
    @Serializable
    object AllProductsRoutes

//    For approve order screen
    @Serializable
    data class ApproveOrderRoutes(val orderId: String)

}
