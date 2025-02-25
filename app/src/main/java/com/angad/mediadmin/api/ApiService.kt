package com.angad.mediadmin.api

import com.angad.mediadmin.models.AddProductResponse
import com.angad.mediadmin.models.DeleteSpecificUserResponse
import com.angad.mediadmin.models.GetAllProductsResponse
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

//    Function that add product details
    @FormUrlEncoded
    @POST("addProduct")
    suspend fun appProduct(
        @Field("name") productName: String?,
        @Field("price") productPrice: Float?,
        @Field("category") productCategory: String?,
        @Field("stock") productStock: Int?
    ): Response<AddProductResponse>

//    Function that fetch all products
    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<GetAllProductsResponse>

}