package com.example.colorapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ColorViewModelFactory(
    private val repository: ColorRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ColorViewModel::class.java)) {
            return ColorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
