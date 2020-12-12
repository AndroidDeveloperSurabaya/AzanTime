package com.example.azantime.shared.di

import org.kodein.di.DI
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
val Injector = DI {
    importAll(
        networkModule,
        repositoryModule
    )
}