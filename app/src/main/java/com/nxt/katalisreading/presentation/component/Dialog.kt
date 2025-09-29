package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Preview
@Composable
fun DialogErrorPreview() {
    MyAppTheme {
        Dialog(
            type = typeDialog.ERROR,
            mes =  "This is an error message",
            onDismiss = {}
        )
    }

}

enum class typeDialog {
    ERROR,
    WARNING,
    INFO,
    SUCCESS
}

@Composable
fun Dialog(
    type: typeDialog = typeDialog.INFO,
    mes: String = "",
    onDismiss: () -> Unit,
    modifier : Modifier = Modifier
) {

        AlertDialog(
            onDismissRequest = onDismiss,
            icon = {
                val icon = when (type) {
                    typeDialog.ERROR -> R.drawable.error
                    typeDialog.WARNING -> R.drawable.warning
                    typeDialog.INFO -> R.drawable.info
                    typeDialog.SUCCESS -> R.drawable.succes
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                        )
                }

            },
            text = {
                Text(
                    text = mes,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
                   },
            confirmButton = {},
            dismissButton = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .wrapContentWidth(),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background,
                            disabledContainerColor =  MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.background,
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
                }

            },

            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.errorContainer

        )

}