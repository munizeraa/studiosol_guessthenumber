package com.mnzlabz.guessthenumber

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GTNApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}