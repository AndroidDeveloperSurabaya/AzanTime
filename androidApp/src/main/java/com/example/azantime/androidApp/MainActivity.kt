package com.example.azantime.androidApp

import java.util.*
import kotlin.math.abs
import android.util.Log
import android.widget.Toast
import android.widget.TextView
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel
import kotlinx.coroutines.MainScope
import kotlin.collections.ArrayList
import com.example.azantime.shared.Timings
import com.example.azantime.shared.DataAzan

class MainActivity : BaseActivity() {
    override fun getLayout(): Int { return R.layout.activity_main }

    private val mainScope = MainScope()
    var data = DataAzan()
    lateinit var tvDuhurTime  : TextView
    lateinit var tvAsharTime  : TextView
    lateinit var tvMagribTime : TextView
    lateinit var tvIshaTime   : TextView
    lateinit var tvSubuhTime  : TextView
    lateinit var tvTimer      : TextView
    lateinit var tvTodayDate  : TextView
    lateinit var tvNextPray   : TextView
    var latitude  = -7.2756141
    var longitude = -112.642642

    override fun OnMain() {
        tvDuhurTime = findViewById(R.id.tv_duhur_time) as TextView
        tvAsharTime = findViewById(R.id.tv_asar_time) as TextView
        tvMagribTime = findViewById(R.id.tv_magrib_time) as TextView
        tvIshaTime = findViewById(R.id.tv_isha_time) as TextView
        tvSubuhTime = findViewById(R.id.tv_subuh_time) as TextView
        tvTodayDate = findViewById(R.id.tv_today_date) as TextView
        tvTimer     = findViewById(R.id.tv_timer) as TextView
        tvNextPray  = findViewById(R.id.tv_next_pray) as TextView

        getDataAzan()
    }

    private fun getDataAzan() {
        tvTodayDate.text = "${getDate("MMMM dd , yyyy")}. 27 Rabi ul Awal, 1442"
        mainScope.launch {
            kotlin.runCatching {
                data.getLaunches(System.currentTimeMillis(),latitude,longitude)
            }.onSuccess {

                tvSubuhTime.text = it.data?.timings?.fajr.toString()
                tvDuhurTime.text = it.data?.timings?.dhuhr.toString()
                tvAsharTime.text = it.data?.timings?.asr.toString()
                tvMagribTime.text = it.data?.timings?.maghrib.toString()
                tvIshaTime.text = it.data?.timings?.isha.toString()

                setTimeNextPray(it.data?.timings)
            }.onFailure {
                Log.e("TAG",it.localizedMessage)
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setTimeNextPray(it: Timings?) {
        var dataPrayer = ArrayList<String>()
        dataPrayer.add(it?.fajr.toString())
        dataPrayer.add(it?.dhuhr.toString())
        dataPrayer.add(it?.asr.toString())
        dataPrayer.add(it?.maghrib.toString())
        dataPrayer.add(it?.isha.toString())

        val currentIndex = getCurrentTimePrayIndex(dataPrayer)
        var timePrayPosition = currentIndex+1
        if (timePrayPosition==5){
            timePrayPosition = 0
        }
        var currentTimePray = getDeltaLastPrayerTime(dataPrayer,timePrayPosition)
        var currentTimeBeforePray = getDeltaLastPrayerTime(dataPrayer,currentIndex)

        setProgresBar(timePrayPosition,currentTimeBeforePray.toInt(),currentTimePray.toInt())
        setNextPray(timePrayPosition)
    }


    private fun setProgresBar(timePrayPosition: Int,currentTimePray: Int,timecount: Int) {

        //timecount = millisecond
        CountdownTime().countDown(timecount,object : CountdownTime.callBackCountDownTime{
            override fun onTick(millisUntilFinished: Long, hours: Int, minutes: Int, seconds: Int) {
                val presentage = currentTimePray.toFloat() / (currentTimePray.toFloat()+millisUntilFinished.toFloat()) * 100

                if (hours==0&&minutes==0&&seconds==0){
                    // next pray
                    setNextPray(timePrayPosition)
                }

                else if(hours==0&&minutes==0){
                   tvTimer.text = "${seconds} SECOND REMAINING"
                }
                else if(hours==0){
                    tvTimer.text = "${minutes} MINUTES ${seconds} SECOND REMAINING"
                }
                else {
                    tvTimer.text = "${hours} HOURS ${minutes} MINUTES ${seconds} SECOND REMAINING"
                }
            }

            override fun onFinish() {
            }
        })

    }

    private fun setNextPray(timePrayPosition: Int) {
        when(timePrayPosition){
            0 -> {
                tvNextPray.text = "Fajr"
            }
            1 -> {
                tvNextPray.text = "Dhuhr"
            }
            2 -> {
                tvNextPray.text = "Asr"
            }
            3 -> {
                tvNextPray.text = "sunset"
            }
            4 -> {
                tvNextPray.text = "isha"
            }
        }
    }

    private fun getCurrentTimePrayIndex(dataPrayer: java.util.ArrayList<String>): Int {
        var currentIndex = 5
        for (i in 0..dataPrayer.size-1){
            var timeArray = dataPrayer.get(i).split(":")

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
            calendar.set(Calendar.MINUTE, timeArray[1].toInt())
            calendar.set(Calendar.SECOND,0)

            if(calendar> Calendar.getInstance()){
                currentIndex = i - 1
                break
            }
        }

        if(currentIndex==-1) currentIndex = 5
        return currentIndex
    }

    private fun getDeltaLastPrayerTime(dataPrayer : ArrayList<String>, nextTimePray:Int): Long {

        val timeArray = dataPrayer.get(nextTimePray).split(":")
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
        calendar.set(Calendar.MINUTE, timeArray[1].toInt())
        calendar.set(Calendar.SECOND,0)


        val timeNow = Calendar.getInstance()

        // when isha'  (isha' today and subuh next day)
        if(nextTimePray==0){
            calendar.add(Calendar.DATE,1)
        }
        val deltaMillis = abs(calendar.timeInMillis - timeNow.timeInMillis)
        return deltaMillis
    }


    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
