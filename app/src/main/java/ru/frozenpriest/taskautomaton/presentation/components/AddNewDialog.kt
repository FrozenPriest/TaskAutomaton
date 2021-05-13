package ru.frozenpriest.taskautomaton.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun AddNewDialog(
    showDialog: Boolean,
    setShowDialog: (Boolean) -> Unit,
    text: String,
    onConfirm: (String) -> Unit
) {
    val (textField, setText) = remember { mutableStateOf(TextFieldValue(text)) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Program")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(textField.text)
                        setShowDialog(false)
                    },
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        setShowDialog(false)
                    },
                ) {
                    Text("Cancel")
                }
            },
            text = {
                TextField(
                    value = textField,
                    onValueChange = {setText(it)}
                )
            },
        )
    }
}