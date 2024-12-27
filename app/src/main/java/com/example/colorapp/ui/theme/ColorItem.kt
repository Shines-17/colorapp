package com.example.colorapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.colorapp.database.ColorEntry

@Composable
fun ColorItem(color: ColorEntry) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(color.color))
        )
    ) {
        Text(
            text = color.color,
            color = Color.White, // Set text color to ensure readability on a colored background
            modifier = Modifier.padding(16.dp)
        )
    }
}