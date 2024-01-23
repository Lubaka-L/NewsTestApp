package com.example.testapp

import android.app.Application
import com.example.data.di.networkModule
import com.example.data.di.dataModule
import com.example.data.di.databaseModule
import com.example.testapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(networkModule, dataModule, databaseModule, viewModelModule)
        }

    }

}