package com.example.bitfit

import android.app.Application

class HealthEntryApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}