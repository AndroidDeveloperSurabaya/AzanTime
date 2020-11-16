package com.example.azantime.androidApp

import java.util.*
import android.util.Log
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import android.content.Intent
import android.content.Context
import android.app.AlertDialog
import android.view.WindowManager
import java.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by khoiron on 18/02/18.
 */

abstract class BaseActivity : AppCompatActivity(){

    protected var statusInternet : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayout())

        OnMain()
    }


    abstract fun OnMain()
    abstract fun getLayout(): Int


    fun setLog(message: String){
        Log.e("Test",message)
    }

    fun setToast(message: String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    fun closeApplication(){
        AlertDialog.Builder(this)
                .setMessage("Apa Anda yakin ingin menutup aplikasi?")
                .setCancelable(false)
                .setPositiveButton("IYA") { dialog, id ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("Tidak", null)
                .show()
    }

    fun closeApplication(message: String){
        AlertDialog.Builder(this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("IYA") { dialog, id ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("Tidak", null)
                .show()
    }

    fun gotoActivity(clas : Class<*>?){
        startActivity(Intent(this,clas))
    }

    fun gotoActivityWithBundle(clas : Class<*>?,bundle: Bundle){
        var intent = Intent(this,clas)
        intent.putExtra("data",bundle)
        startActivity(intent)
    }

    fun getDate():String{
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat("dd/MM/yyyy")
        val strDate  = mdformat.format(calendar.getTime())
        return strDate
    }

    fun getDate(formatOutput:String):String{
        val calendar = Calendar.getInstance()
        val mdformat = SimpleDateFormat(formatOutput)
        val strDate  = mdformat.format(calendar.getTime())
        return strDate
    }

    fun getDate(dateString:String,formatInput:String,formatOutput:String):String{
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat(formatInput).parse(dateString)
        val mdformat = SimpleDateFormat(formatOutput)
        val strDate  = mdformat.format(calendar.getTime())
        return strDate
    }

    @SuppressLint("MissingPermission")
    fun checkInterConection(){
        var connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkinfo = connMgr.activeNetworkInfo
        if (networkinfo != null && networkinfo.isConnected()) {
            statusInternet = true
            setLog(statusInternet.toString())
        } else {
            statusInternet = false
        }
    }

}