package com.example.bitfit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_entry_table")
data class HealthEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "sleep_hours") val sleepHours: Float?,
    @ColumnInfo(name = "mood") val mood: Float?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "date") val date: String?
)