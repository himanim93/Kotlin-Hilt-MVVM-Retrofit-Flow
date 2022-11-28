package com.hm.users.utils

import android.app.Application
import com.hm.users.UserApplication

object AppData {

    lateinit var application: Application

    fun init(application: UserApplication) {
        AppData.application = application
    }

    object Error {
        const val NO_INTERNET_CONNECTION = -1
        const val NETWORK_ERROR = -2
    }
}