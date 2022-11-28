package com.hm.users.remote

import com.hm.users.data.model.UserDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UserAPI {
    @Headers("Accept: application/vnd.github.+json")
    @GET("search/users")
    suspend fun getUserList(@Query("q") query: String,
                            @Query("page") page: Int,
                            @Query("per_page") pageSize: Int): Response<UserDetails>
}