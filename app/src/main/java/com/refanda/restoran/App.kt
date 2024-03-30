package com.refanda.restoran

import android.app.Application
import com.refanda.restoran.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}