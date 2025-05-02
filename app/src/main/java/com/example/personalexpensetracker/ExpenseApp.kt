package com.example.personalexpensetracker

import android.app.Application
import com.example.personalexpensetracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExpenseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ExpenseApp)
            modules(appModule)
        }
    }
}