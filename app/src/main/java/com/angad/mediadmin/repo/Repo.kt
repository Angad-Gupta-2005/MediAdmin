package com.angad.mediadmin.repo

import com.angad.mediadmin.api.ApiBuilder
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.GetSpecificUser
import com.angad.mediadmin.models.UpdateUserDetailsResponse
import com.angad.mediadmin.models.UserModels
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val apiBuilder: ApiBuilder){

//    Function that fetch all user details
    suspend fun getAllUsers(): Flow<Results<Response<UserModels>>> = flow{
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllUsers()
            emit(Results.Success(response))
        } catch (e: Exception){
            emit(Results.Error(e.message.toString()))
        }

    }

//    Function that fetch specific user details

    suspend fun getSpecificUser(id: String): Flow<Results<Response<GetSpecificUser>>> = flow{
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getSpecificUser(id)
            if (response.isSuccessful){
                emit(Results.Success(response))
            } else{
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }



//    Function that delete specific user
    suspend fun deleteSpecificUser(id: String): Flow<Results<Response<UserModels>>> = flow {
        emit(Results.Loading)

        try {
            val response = apiBuilder.api.deleteSpecificUser(id)
            if (response.isSuccessful) {
                emit(Results.Success(response))
            } else {
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Results.Error(e.message.toString()))
        }
    }





//    Function that update user details

    suspend fun updateUserInfo(
        user_id: String?,
        name: String?,
        email: String?,
        password: String?,
        address: String?,
        pin_code: String?,
        phone_number: String?,
        isApproved: Int?,
        date_of_account_creation: String?,
        block: Int?
    ): Flow<Results<Response<UpdateUserDetailsResponse>>> = flow{
        emit(Results.Loading)

        try {
            val response = apiBuilder.api.updateUserAllDetails(
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
            )
            if (response.isSuccessful){
                emit(Results.Success(response))
            } else{
                emit(Results.Error(response.message()))
            }
        } catch (e: Exception){
            emit(Results.Error(e.message.toString()))
        }
    }



}