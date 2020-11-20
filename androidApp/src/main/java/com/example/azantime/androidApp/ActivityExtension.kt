package com.example.azantime.androidApp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T: AppCompatActivity> Context.gotoActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) intent.putExtras(bundle)
    startActivity(intent)
}