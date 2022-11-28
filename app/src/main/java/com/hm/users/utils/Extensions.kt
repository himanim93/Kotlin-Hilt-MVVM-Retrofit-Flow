package com.hm.users.utils

import android.app.Activity
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


/**
 * Extension method to set Status Bar Color and Status Bar Icon Color Type(dark/light)
 */
enum class StatusIconColorType {
    Dark, Light
}

fun Activity.setStatusBarColor(
    color: Int,
    iconColorType: StatusIconColorType = StatusIconColorType.Light
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            statusBarColor = color

            val wic = WindowInsetsControllerCompat(this, decorView)
            wic.isAppearanceLightStatusBars = when (iconColorType) {
                StatusIconColorType.Dark -> false
                StatusIconColorType.Light -> true
            }
        }
    } else
        this.window.statusBarColor = color
}

/**
 * Set a Ternary condition
 */
class Ternary<T>(val expr: Boolean, val then: T) {
    operator fun div(elze: T): T = if (expr) then else elze
}

operator fun <T> Boolean.rem(a: T): Ternary<T> = Ternary(this, a)

/**
 * Set an onclick listener
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }

/**
 * EditText after text changed
We can pass the function to be performed after edit text input changes as closure.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.toString()?.let { afterTextChanged.invoke(it) }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { // na
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { // na
        }
    })
}

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { it?.let { t -> action(t) } })
}
