package com.example.bitfit

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class HealthEntry(
        val sleepHours: Float?,
        val mood: Float?,
        val notes: String?,
        val date: String?
) : java.io.Serializable