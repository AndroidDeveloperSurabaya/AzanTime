package com.example.azantime.androidApp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    @SuppressLint("SimpleDateFormat")
    fun getDate():String{
        val calendar = Calendar.getInstance()
        val mdFormat = SimpleDateFormat("dd/MM/yyyy")
        return mdFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(formatOutput:String):String{
        val calendar = Calendar.getInstance()
        val mdFormat = SimpleDateFormat(formatOutput)
        return mdFormat.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(dateString: String, formatInput:String, formatOutput:String):String{
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat(formatInput).parse(dateString) ?: Date()
        val mdFormat = SimpleDateFormat(formatOutput)
        return mdFormat.format(calendar.time)
    }
}