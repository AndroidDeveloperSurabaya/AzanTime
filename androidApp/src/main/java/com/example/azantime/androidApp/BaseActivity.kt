package com.example.azantime.androidApp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.DIContext
import org.kodein.di.android.closestDI
import org.kodein.di.bindings.Reference
import org.kodein.di.diContext


/**
 * Created by khoiron on 18/02/18.
 */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), DIAware {
	
	protected var statusInternet: Boolean = false
	protected val viewBinding: VB by lazy { bindLayout() }
	override val di: DI by closestDI()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
		setContentView(viewBinding.root)
		
		onMain()
	}
    
    abstract fun bindLayout(): VB
	
	
	abstract fun onMain()
	
	protected fun setLog(message: String) {
		Log.e("Test", message)
	}
	
	fun setToast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
	}
	
	fun closeApplication() {
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
	
	fun closeApplication(message: String) {
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
	fun checkInterConnection() {
		val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		val networkInfo = connMgr.activeNetworkInfo
		
		statusInternet = networkInfo != null && networkInfo.isConnected
	}
	
}