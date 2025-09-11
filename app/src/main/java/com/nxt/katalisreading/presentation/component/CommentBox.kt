package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nxt.katalisreading.presentation.theme.MyAppTheme

@Composable
fun CommentBox(
    maxLength: Int = 200,
    placeholder: String = "Nhập bình luận",
    send: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(

        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        text = newText
                    }
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                maxLines = 4
            )
            // Đếm số ký tự
            Text(
                text = "${text.length} / $maxLength",
                style = MaterialTheme.typography.bodySmall,
                color = if (text.length >= maxLength) Color.Red else Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 5.dp, bottom = 5.dp)
            )
        }


        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { send },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                )
                .height(32.dp)
                .align(Alignment.End ),


        ) {
            Text(
                text = "Gửi",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.background
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CommentBoxPreview() {
    MyAppTheme {
        CommentBox()
    }
}