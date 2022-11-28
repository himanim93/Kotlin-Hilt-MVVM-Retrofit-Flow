package com.hm.users.remote

sealed class Resource<T>(
        val data: T? = null,
        val errorString: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class DataError<T>(errorMsg: Int) : Resource<T>(null, errorMsg)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorString]"
        }
    }
}


