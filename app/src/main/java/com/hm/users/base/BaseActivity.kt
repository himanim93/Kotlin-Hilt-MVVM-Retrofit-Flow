package com.hm.users.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.findViewById<View>(android.R.id.content).isFocusableInTouchMode = true
        initViewBinding()
        observeViewModel()
        onActionPerform()
    }

    protected abstract fun initViewBinding()
    abstract fun observeViewModel()
    open fun onActionPerform() {}

}