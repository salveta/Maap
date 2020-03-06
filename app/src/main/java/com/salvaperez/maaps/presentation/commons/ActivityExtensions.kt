package com.salvaperez.maaps.presentation.commons

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

fun Activity.open(cls: Class<*>, extras: Bundle? = null) {
    val intent = Intent(this, cls)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}