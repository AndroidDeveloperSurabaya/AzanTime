package com.example.azantime.androidApp

import android.os.CountDownTimer

class CountdownTime {

    fun countDown(deltaTime: Int,callBackCountDownTime: CallBackCountDownTime) {
        val mCountDownTimer: CountDownTimer
        var i = 0

        mCountDownTimer = object : CountDownTimer(deltaTime.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                callBackCountDownTime.onTick(millisUntilFinished,
                        convertMillisToHour(millisUntilFinished),
                        convertMillisToMinutes(millisUntilFinished),
                        convertMillisToSecond(millisUntilFinished))
                i++
            }

            override fun onFinish() {
                i++
                callBackCountDownTime.onFinish()
            }
        }
        mCountDownTimer.start()
    }

    private fun convertMillisToHour(millisUntilFinished: Long):Int {
        return (millisUntilFinished / (1000 * 60 * 60) % 24).toInt()
    }

    private fun convertMillisToMinutes(millisUntilFinished: Long):Int {
        return (millisUntilFinished / (1000 * 60) % 60).toInt()
    }

    private fun convertMillisToSecond(millisUntilFinished: Long):Int {
        return ((millisUntilFinished / 1000) % 60).toInt()
    }

    interface CallBackCountDownTime{
        fun onTick(millisUntilFinished:Long,hours:Int,minutes:Int,seconds:Int)
        fun onFinish()
    }

}