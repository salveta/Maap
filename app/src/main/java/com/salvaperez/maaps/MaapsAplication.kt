package com.salvaperez.maaps

import android.app.Application
import com.salvaperez.maaps.di.initDI

class MaapsAplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}