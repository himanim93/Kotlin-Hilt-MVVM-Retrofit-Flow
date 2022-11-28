package com.hm.users.data

import com.hm.users.data.model.UserDetails
import com.hm.users.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

open class UserRepository @Inject constructor(private val dataSource: UserDataSource) {

    open suspend fun getUserList(): Flow<Resource<UserDetails>> = flow {
        emit(dataSource.getUserList())
    }.flowOn(Dispatchers.IO)

}