package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun Loading(
    isLoading: Boolean = true,
    modifier: Modifier = Modifier
) {
    if (!isLoading) return
    androidx.activity.compose.BackHandler(enabled = true){

    }
    Box(
        modifier= modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.35f))
            .consumeAllInput(),

        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 6.dp
        )
    }
}

// Extension để nuốt (consume) mọi sự kiện chạm/scroll/drag
fun Modifier.consumeAllInput(): Modifier = composed {
    pointerInput(Unit) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                // consume tất cả thay đổi để không “lọt” xuống dưới
                event.changes.forEach { it.consume() }
            }
        }
    }
}