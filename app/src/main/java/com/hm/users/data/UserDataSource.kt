package com.hm.users.data

import com.hm.users.R
import com.hm.users.data.model.APIError
import com.hm.users.data.model.UserDetails
import com.hm.users.remote.UserAPI
import com.hm.users.remote.Resource
import com.hm.users.utils.AppData
import com.hm.users.utils.NetworkUtils
import retrofit2.Retrofit
import javax.inject.Inject

open class UserDataSource @Inject constructor(
    private val retrofit: Retrofit,
    private val userAPI: UserAPI
) {
    open suspend fun getUserList(): Resource<UserDetails> {
        return when (val response =
            NetworkUtils.processCall(retrofit) { (userAPI::getUserList)("android",1,10) }) {
            is UserDetails -> {
                Resource.Success(data = response)
            }
            is APIError -> {
                Resource.DataError(
                    errorMsg = (R.string.something_wrong)
                )
            }
            else -> {
                return Resource.DataError(errorMsg = if (response == AppData.Error.NO_INTERNET_CONNECTION) AppData.Error.NO_INTERNET_CONNECTION else (R.string.something_wrong))
            }
        }
    }
}
