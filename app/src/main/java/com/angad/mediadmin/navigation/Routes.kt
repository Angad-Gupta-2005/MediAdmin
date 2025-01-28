package com.angad.mediadmin.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object AllUsersUIRoutes

    @Serializable
    data class UserDetailsRoutes(
        val user_id: String
    )

}
