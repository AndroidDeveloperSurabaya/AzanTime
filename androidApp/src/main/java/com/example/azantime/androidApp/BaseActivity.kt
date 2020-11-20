package com.example.azantime.androidApp

import android.util.Log
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.content.Context
import android.app.AlertDialog
import android.net.ConnectivityManager
import android.annotation.SuppressLint
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * Created by khoiron on 18/02/18.
 */

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity(){

    protected var statusInternet : Boolean = false
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = DataBindingUtil.setContentView(this, getLayout())

        onMain()
    }


    abstract fun onMain()
    abstract fun getLayout(): Int

    protected fun setLog(message: String){
        Log.e("Test",message)
    }

    fun setToast(message: String){
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    fun closeApplication(){
        AlertDialog.Builder(this)
                .setMessage("Apa Anda yakin ingin menutup aplikasi?")
                .setCancelable(false)
                .setPositiveButton("IYA") { _, _ ->
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
                .setPositiveButton("IYA") { _, _ ->
                    val exit = Intent(Intent.ACTION_MAIN)

                    exit.addCategory(Intent.CATEGORY_HOME)

                    exit.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                    startActivity(exit)
                }
                .setNegativeButton("Tidak", null)
                .show()
    }

    @SuppressLint("MissingPermission")
    fun checkInterConnection(){
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connMgr.activeNetworkInfo

        statusInternet = networkInfo != null && networkInfo.isConnected
    }

}