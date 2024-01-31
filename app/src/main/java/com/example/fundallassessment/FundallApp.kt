package com.example.fundallassessment

import android.app.Application
import com.dsofttech.dprefs.utils.DPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FundallApp(): Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Shared Preference Library
        DPrefs.initializeDPrefs(this)
    }
}