package com.shailesh.icewarptask.ui.channel.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ExitDialog(onDismiss: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Exit", fontWeight = FontWeight.Bold) },
        text = { Text(text = "Are you sure you want to Exit?") },
        confirmButton = {
            TextButton(onClick = onExit) {
                Text(
                    text = "Yes",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("No") } })
}