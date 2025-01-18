package com.rajabi.starwars.util

import kotlinx.coroutines.flow.Flow

//sealed class Resource<out T> : Flow<Any> {
//    object Loading : Resource<Nothing>()
//    data class Success<out T>(val data: T) : Resource<T>()
//    data class Failure(val message: String, val cause: Throwable? = null) : Resource<Nothing>()
//}
sealed class Resource<out T> {
    data object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val message: String) : Resource<Nothing>()
}