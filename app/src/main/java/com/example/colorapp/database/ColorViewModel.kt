package com.example.colorapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {
    val colors = MutableStateFlow<List<ColorEntry>>(emptyList())
    val pendingCount = MutableStateFlow(0)

    init {
        fetchColors()
    }

    private fun fetchColors() {
        viewModelScope.launch {
            colors.value = repository.getAllColors()
            updatePendingCount()
        }
    }

    fun addColor() {
        viewModelScope.launch {
            val colorEntry = generateRandomColor()
            repository.insertColor(colorEntry)
            fetchColors() // Refresh colors after insertion
        }
    }

    fun syncData() {
        viewModelScope.launch {
            try {
                // Sync with Firebase
                repository.syncWithServer(colors.value)

                // Clear local database after successful sync
                repository.clearAllColors()

                // Refresh UI state
                fetchColors()
                pendingCount.value = 0
            } catch (e: Exception) {
                e.printStackTrace() // Handle errors during syncing
            }
        }
    }

    private fun updatePendingCount() {
        pendingCount.value = colors.value.count { it.syncStatus == 0 }
    }

    private fun generateRandomColor(): ColorEntry {
        val randomColor = "#" + List(6) { "0123456789ABCDEF".random() }.joinToString("")
        return ColorEntry(
            color = randomColor,
            time = System.currentTimeMillis(),
            syncStatus = 0
        )
    }
}