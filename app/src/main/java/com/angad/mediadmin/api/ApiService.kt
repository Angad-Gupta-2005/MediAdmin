package com.angad.mediadmin.api

import com.angad.mediadmin.models.DeleteSpecificUserResponse
import com.angad.mediadmin.models.GetSpecificUser
import com.angad.mediadmin.models.UpdateUserDetailsResponse
import com.angad.mediadmin.models.UserModels
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {


    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<UserModels>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("user_id") user_id: String
    ): Response<GetSpecificUser>

//    Function that delete specific user
    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "deleteSpecificUser", hasBody = true)
    suspend fun deleteSpecificUser(
        @Field("user_id") user_id: String
    ): Response<DeleteSpecificUserResponse>



//    Function that update specific user's all details
    @FormUrlEncoded
    @PATCH("updateUserAllDetails")
    suspend fun updateUserAllDetails(
        @Field("user_id") user_id: String?,
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("address") address: String?,
        @Field("pin_code") pin_code: String?,
        @Field("phone_number") phone_number: String?,
        @Field("isApproved") isApproved: Int?,
        @Field("date_of_account_creation") date_of_account_creation: String?,
        @Field("block") block: Int?
    ): Response<UpdateUserDetailsResponse>


//    Function that approved the user by admin
    @FormUrlEncoded
    @PATCH("approveUser")
    suspend fun approveUser(
        @Field("user_id") user_id: String?,
        @Field("isApproved") isApproved: Int?
    ): Response<UpdateUserDetailsResponse>

}