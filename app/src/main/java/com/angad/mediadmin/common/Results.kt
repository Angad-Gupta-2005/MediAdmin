package com.angad.mediadmin.common

sealed class Results<out T> {

    object Loading: Results<Nothing>()
    data class Success<out T>(val data: T): Results<T>()
    data class Error(val message: String): Results<Nothing>()

}