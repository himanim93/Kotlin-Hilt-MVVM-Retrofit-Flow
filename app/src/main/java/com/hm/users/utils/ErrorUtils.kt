package com.hm.users.utils

import com.hm.users.data.model.APIError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class ErrorUtils {
    fun parseError(retrofit: Retrofit, response: Response<*>): APIError? {
        val converter: Converter<ResponseBody, APIError> = retrofit
            .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))
        return try {
            converter.convert(response.errorBody())
        } catch (e: IOException) {
            return APIError()
        }
    }
}