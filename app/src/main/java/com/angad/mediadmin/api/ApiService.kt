package com.angad.mediadmin.api

import com.angad.mediadmin.models.GetSpecificUser
import com.angad.mediadmin.models.UserModels
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {


    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<UserModels>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") user_id: String
    ): Response<GetSpecificUser>

}