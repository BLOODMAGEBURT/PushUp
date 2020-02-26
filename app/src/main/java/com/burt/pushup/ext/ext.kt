package com.burt.pushup.ext

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun hell(){
    Log.d("aa", "is this ok ?")
}