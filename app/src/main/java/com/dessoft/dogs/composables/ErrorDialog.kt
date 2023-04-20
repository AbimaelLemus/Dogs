package com.dessoft.dogs.composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dessoft.dogs.R


@Composable
fun ErrorDialog(
    messageId: Int,
    onDialogDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(stringResource(id = R.string.error_dialog_title))
        },
        text = {
            Text(stringResource(id = messageId))
        },
        confirmButton = {
            Button(onClick = { onDialogDismiss() }) {
                Text(stringResource(id = R.string.try_again))
            }
        }
    )
}