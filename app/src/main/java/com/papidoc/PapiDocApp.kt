package com.papidoc

import android.app.Application
import com.papidoc.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PapiDocApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PapiDocApp)
            modules(appModule)
        }
    }
}
