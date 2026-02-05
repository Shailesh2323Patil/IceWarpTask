package com.shailesh.icewarptask.ui.channel.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onLogout: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Logout", fontWeight = FontWeight.Bold) },
        text = { Text(text = "Are you sure you want to logout?") },
        confirmButton = {
            TextButton(onClick = onLogout) {
                Text(
                    text = "Logout",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } })
}