package com.example.azantime.shared.repository

import com.example.azantime.shared.provider.KMMContextWrapper
import com.example.azantime.shared.provider.ContextProvider


actual object RepositoryFactory {

    private val KMM_CONTEXT_WRAPPER: KMMContextWrapper by lazy { ContextProvider.KMMContextWrapper }

    actual fun createLocationRepository(): LocationRepository {
        return AndroidLocationRepository(KMM_CONTEXT_WRAPPER.appContext)
    }
}