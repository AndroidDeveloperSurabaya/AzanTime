package com.example.azantime.shared

class DataAzan {

    private val api = NetworkApi()

    @Throws(Exception::class) suspend fun getLaunches(timesTime:Long,latitude:Double,longitude: Double): AzanEntity {
        var query = "timings/${timesTime}?latitude=${latitude}&longitude=${longitude}&method=5"
        api.getAllLaunches(query).also {
            return it
        }
    }

}