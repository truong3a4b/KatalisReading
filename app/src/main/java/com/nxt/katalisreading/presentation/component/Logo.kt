package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nxt.katalisreading.R

@Composable
fun Logo(
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.bookfull),
            contentDescription = null
        )
        Text(
            text = "Katalis",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(start = 10.dp)
        )
    }
}