package com.nxt.katalisreading.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = {
            Text(
                text = "Nhập email",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        modifier = modifier,
        singleLine = true
    )
}