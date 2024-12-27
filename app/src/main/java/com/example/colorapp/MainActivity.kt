package com.example.colorapp

import ColorScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.colorapp.database.AppDatabase
import com.example.colorapp.database.ColorRepository
import com.example.colorapp.ui.theme.ColorAppTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Analytics
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Log a custom event for Firebase Analytics
        logFirebaseAnalyticsEvent()

        // Firebase Database setup
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().reference.child("testConnection").setValue("Success!")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseTest", "Connected to Firebase successfully!")
                } else {
                    Log.e("FirebaseTest", "Failed to connect to Firebase", task.exception)
                }
            }

        // Initialize the ColorRepository
        val database = AppDatabase.getInstance(applicationContext)
        val repository = ColorRepository(database.colorDao())

        setContent {
            ColorAppTheme {
                ColorScreen(repository = repository)
            }
        }
    }

    private fun logFirebaseAnalyticsEvent() {
        // Log a custom event to Firebase Analytics
        val bundle = Bundle()
        bundle.putString("example_key", "example_value")
        firebaseAnalytics.logEvent("example_event", bundle)
        Log.d("FirebaseAnalytics", "Logged example_event to Firebase Analytics")
    }
}