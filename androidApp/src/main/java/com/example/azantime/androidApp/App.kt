package com.example.azantime.androidApp

import android.app.Application
import com.example.azantime.shared.provider.KMMContextWrapper
import com.example.azantime.shared.provider.ContextProvider


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ContextProvider.provideContext(KMMContextWrapper(this))
    }
}