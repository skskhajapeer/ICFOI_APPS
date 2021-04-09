package com.roomdb.example.utils

import android.app.Application

class AComputerEngineerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}