package com.nxt.katalisreading.presentation.component

import android.app.Dialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Preview
@Composable
fun DialogErrorPreview() {
    MyAppTheme {
        DialogError(
            error = "This is an error message",
            onDismiss = {}
        )
    }

}

@Composable
fun DialogError(
    error: String?,
    onDismiss: () -> Unit
) {
    if (error != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium
                )
                   },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .wrapContentWidth(),
                    colors = ButtonColors(
                        containerColor = Color(0xFF0281E1),
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color(0xFF0281E1),
                        disabledContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.errorContainer

        )
    }
}