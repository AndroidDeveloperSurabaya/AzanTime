package com.example.azantime.androidApp

import android.app.Application
import com.example.azantime.shared.di.networkModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule


class App : Application(), DIAware {

    override val di: DI by DI.lazy {
        importAll(
            androidXModule(this@App),
            networkModule
        )
    }
}