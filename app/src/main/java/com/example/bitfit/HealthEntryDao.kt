package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthEntryDao {
    @Query("SELECT * FROM health_entry_table")
    fun getAll(): Flow<List<HealthEntryEntity>>

    @Insert
    fun insertAll(entries: List<HealthEntryEntity>)

    @Insert
    fun insert(entry: HealthEntryEntity)

    @Query("DELETE FROM health_entry_table")
    fun deleteAll()

    @Query("SELECT AVG(sleep_hours) FROM health_entry_table")
    fun getAverageSleep(): Float

    @Query("SELECT AVG(mood) FROM health_entry_table")
    fun getAverageMood(): Float
}