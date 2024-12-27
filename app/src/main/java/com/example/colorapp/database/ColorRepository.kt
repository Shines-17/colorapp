package com.example.colorapp.database

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ColorRepository(private val colorDao: ColorDao) {

    private val database = FirebaseDatabase.getInstance().reference.child("colors")

    // Retrieve all colors from the local database
    suspend fun getAllColors(): List<ColorEntry> = withContext(Dispatchers.IO) {
        colorDao.getAllColors()
    }

    // Insert a new color into the local database
    suspend fun insertColor(colorEntry: ColorEntry) = withContext(Dispatchers.IO) {
        colorDao.insertColor(colorEntry)
    }

    // Sync colors with Firebase Realtime Database
    suspend fun syncWithServer(colors: List<ColorEntry>) = withContext(Dispatchers.IO) {
        try {
            // Push all colors to Firebase
            database.setValue(colors)
        } catch (e: Exception) {
            // Handle any errors that occur during the sync process
            e.printStackTrace()
        }
    }

    // Clear all colors from the local database (optional, for post-sync cleanup)
    suspend fun clearAllColors() = withContext(Dispatchers.IO) {
        colorDao.clearAllColors()
    }
}