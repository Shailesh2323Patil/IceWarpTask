package com.shailesh.icewarptask.ui.channel.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.shailesh.icewarptask.R

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onLogout: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.logout), fontWeight = FontWeight.Bold) },
        text = { Text(text = stringResource(R.string.logout_message)) },
        confirmButton = {
            TextButton(onClick = onLogout) {
                Text(
                    text = stringResource(R.string.logout),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } })
}