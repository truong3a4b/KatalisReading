package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.theme.MyAppTheme

@Composable
@Preview
fun PassWordFieldPreview() {
    MyAppTheme {
        PassWordField(
            password = "",
            onPasswordChange = {},
            placeholder = "Nhập mật khẩu",
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }

}

@Composable
fun PassWordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean = false,
    errorMes: String = "",
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = errorMes,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },

        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    painter = if (visible) painterResource(R.drawable.eye) else painterResource(R.drawable.eye_slash),
                    contentDescription = null
                )
            }
        },
        modifier = modifier,
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}