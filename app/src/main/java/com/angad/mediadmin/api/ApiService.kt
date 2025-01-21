package com.angad.mediadmin.api

import com.angad.mediadmin.models.GetAllUsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>

}