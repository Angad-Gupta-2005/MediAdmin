package com.angad.mediadmin.repo

import com.angad.mediadmin.api.ApiBuilder
import com.angad.mediadmin.common.Results
import com.angad.mediadmin.models.GetAllUsersResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class Repo @Inject constructor(private val apiBuilder: ApiBuilder){

    suspend fun getAllUsers(): Flow<Results<Response<GetAllUsersResponse>>> = flow{
        emit(Results.Loading)
        try {
            val response = apiBuilder.api.getAllUsers()
            emit(Results.Success(response))
        } catch (e: Exception){
            emit(Results.Error(e.message.toString()))
        }

    }
}