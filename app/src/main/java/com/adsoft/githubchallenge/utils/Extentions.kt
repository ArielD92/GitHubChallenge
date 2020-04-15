package com.adsoft.githubchallenge.utils

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun String.formatDate() = substringBefore("T")

fun Context.displayToast(message: String): Toast =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).apply { show() }
