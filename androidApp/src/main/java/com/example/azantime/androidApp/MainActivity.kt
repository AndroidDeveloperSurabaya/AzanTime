package com.example.azantime.androidApp

import android.annotation.SuppressLint
import java.util.*
import kotlin.math.abs
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.azantime.androidApp.databinding.ActivityMainBinding
import com.example.azantime.androidApp.utils.DateTimeUtils
import com.example.azantime.androidApp.utils.TimePrayConstant.ASHR
import com.example.azantime.androidApp.utils.TimePrayConstant.ASHR_TIME_PRAY_POSITION
import com.example.azantime.androidApp.utils.TimePrayConstant.DHUHR
import com.example.azantime.androidApp.utils.TimePrayConstant.DHUHR_TIME_PRAY_POSITION
import com.example.azantime.androidApp.utils.TimePrayConstant.FAJR
import com.example.azantime.androidApp.utils.TimePrayConstant.FAJR_TIME_PRAY_POSITION
import com.example.azantime.androidApp.utils.TimePrayConstant.ISHA
import com.example.azantime.androidApp.utils.TimePrayConstant.ISHA_TIME_PRAY_POSITION
import com.example.azantime.androidApp.utils.TimePrayConstant.MAGHRIB
import com.example.azantime.androidApp.utils.TimePrayConstant.MAGHRIB_TIME_PRAY_POSITION
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList
import com.example.azantime.shared.Timings
import com.example.azantime.shared.DataAzan

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getLayout(): Int { return R.layout.activity_main }

    var data = DataAzan()
    var latitude  = -7.2756141
    var longitude = -112.642642

    override fun onMain() {
        getDataAzan()
    }

    @SuppressLint("SetTextI18n")
    private fun getDataAzan() {
        binding.tvTodayDate.text = "${DateTimeUtils.getDate("MMMM dd , yyyy")}. 27 Rabi ul Awal, 1442"
        lifecycleScope.launch {
            kotlin.runCatching {
                data.getLaunches(System.currentTimeMillis(), latitude, longitude)
            }.onSuccess { azanEntity ->
                binding.tvSubuhTime.text = azanEntity.data?.timings?.fajr.toString()
                binding.tvDhuhrTime.text = azanEntity.data?.timings?.dhuhr.toString()
                binding.tvAshrTime.text = azanEntity.data?.timings?.asr.toString()
                binding.tvMaghribTime.text = azanEntity.data?.timings?.maghrib.toString()
                binding.tvIsyaTime.text = azanEntity.data?.timings?.isha.toString()

                setTimeNextPray(azanEntity.data?.timings)
            }.onFailure { failure ->
                Log.e("TAG", failure.localizedMessage)
                Toast.makeText(this@MainActivity, failure.localizedMessage, Toast.LENGTH_SHORT).show()
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

        setProgressBar(timePrayPosition,currentTimeBeforePray.toInt(),currentTimePray.toInt())
        setNextPray(timePrayPosition)
    }


    private fun setProgressBar(timePrayPosition: Int,currentTimePray: Int,timecount: Int) {

        //timecount = millisecond
        CountdownTime().countDown(timecount,object : CountdownTime.CallBackCountDownTime{
            override fun onTick(millisUntilFinished: Long, hours: Int, minutes: Int, seconds: Int) {
                val presentage = currentTimePray.toFloat() / (currentTimePray.toFloat()+millisUntilFinished.toFloat()) * 100

                if (hours==0&&minutes==0&&seconds==0){
                    // next pray
                    setNextPray(timePrayPosition)
                }

                else if(hours==0 && minutes == 0){
                   binding.tvTimer.text = "${seconds} SECOND REMAINING"
                }
                else if(hours==0){
                    binding.tvTimer.text = "${minutes} MINUTES ${seconds} SECOND REMAINING"
                }
                else {
                    binding.tvTimer.text = "${hours} HOURS ${minutes} MINUTES ${seconds} SECOND REMAINING"
                }
            }

            override fun onFinish() {
            }
        })

    }

    private fun setNextPray(timePrayPosition: Int) {
        binding.tvNextPray.text = when (timePrayPosition) {
            FAJR_TIME_PRAY_POSITION -> FAJR
            DHUHR_TIME_PRAY_POSITION -> DHUHR
            ASHR_TIME_PRAY_POSITION -> ASHR
            MAGHRIB_TIME_PRAY_POSITION -> MAGHRIB
            ISHA_TIME_PRAY_POSITION -> ISHA
            else -> ""
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
        return abs(calendar.timeInMillis - timeNow.timeInMillis)
    }
}
