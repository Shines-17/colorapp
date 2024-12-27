package com.example.colorapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.colorapp.database.ColorEntry

@Dao
interface ColorDao {
    // Insert a color
    @Insert
    suspend fun insertColor(colorEntry: ColorEntry)

    // Retrieve all colors
    @Query("SELECT * FROM color_table")
    suspend fun getAllColors(): List<ColorEntry>

    // Clear all colors
    @Query("DELETE FROM color_table")
    suspend fun clearAllColors(): Int

    // Count unsynced colors
    @Query("SELECT COUNT(*) FROM color_table WHERE syncStatus = 0")
    suspend fun getPendingCount(): Int
}