package com.androidwind.weekly.kotlin

import android.content.Context
import android.widget.Toast

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}