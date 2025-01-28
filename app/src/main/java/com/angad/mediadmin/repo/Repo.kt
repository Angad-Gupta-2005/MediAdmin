package com.angad.mediadmin.repo

import com.angad.mediadmin.api.ApiBuilder
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.GetSpecificUser
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
}