package com.example.offlinetodoapp.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
) {
    val ctx: Context = LocalContext.current

    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = description) },
        onDismissRequest = { onDismiss() },
        confirmButton = {
            OutlinedButton(onClick = {
                onAccept()
                Toast
                    .makeText(ctx,"Task has been removed from the list",Toast.LENGTH_SHORT)
                    .show()
            }) {
                Text(text = "confirm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = { onDismiss() }) {
                Text(text = "dismiss")
            }
        },
    )
}