package com.example.movieapp.common.utils

import retrofit2.Response

/**
 * Created by M.Furkan KÜÇÜK on 4.10.2022
 **/
object RequestHelper {

    @Suppress("UNCHECKED_CAST")
    fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        if (response != null) {
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResult.Success(it) as NetworkResult<T>
                }
            }
        }
        return NetworkResult.Error("ERROR_OCCURRED_TRY_AGAIN")
    }

}