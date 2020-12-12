package com.example.azantime.shared.provider

import kotlin.native.concurrent.ThreadLocal

expect class KMMContextWrapper

@ThreadLocal
object ContextProvider {

    lateinit var KMMContextWrapper: KMMContextWrapper

    fun provideContext(KMMContextWrapper: KMMContextWrapper) {
        this.KMMContextWrapper = KMMContextWrapper
    }
}