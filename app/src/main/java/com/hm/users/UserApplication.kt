package com.hm.users

import android.app.Application
import com.hm.users.utils.AppData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class UserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppData.init(this)
    }
}