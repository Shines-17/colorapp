package com.example.colorapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_table")
data class ColorEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Auto-generated primary key
    val color: String, // Color code like #AABBFF
    val time: Long,    // Timestamp
    val syncStatus: Int,
)