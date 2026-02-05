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
fun ExitDialog(onDismiss: () -> Unit, onExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.exit), fontWeight = FontWeight.Bold) },
        text = { Text(text = stringResource(R.string.exit_message)) },
        confirmButton = {
            TextButton(onClick = onExit) {
                Text(
                    text = stringResource(R.string.yes),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.no)) } })
}