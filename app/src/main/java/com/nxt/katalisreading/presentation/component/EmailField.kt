package com.nxt.katalisreading.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nxt.katalisreading.presentation.theme.MyAppTheme

@Preview
@Composable
fun EmailFieldPreview() {
    MyAppTheme{
        EmailField(
            email = "trr",
            onEmailChange = {},
            isError = false,
            errorMessage = "Email không hợp lệ",
        )
    }
}

@Composable
fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
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