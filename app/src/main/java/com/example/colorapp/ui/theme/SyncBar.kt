package com.example.colorapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SyncBar(pendingCount: Int, onSyncClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text("Pending Syncs: $pendingCount")
        Button(onClick = onSyncClick) {
            Text("Sync")
        }
    }
}