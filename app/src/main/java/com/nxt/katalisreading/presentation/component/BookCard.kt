package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Composable
fun BookCardColumn(
    book: Book,
    showType: Boolean = false,
    progress: String = "",
    showProgress: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(4.dp)),

            ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.image)
                    .crossfade(true)
                    .placeholder(R.drawable.placeholder1)
                    .error(R.drawable.placeholder1)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.FillBounds,

                )
            if (showType) {
                Text(
                    text = book.type,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 4.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 4.dp
                            )
                        )
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp)

                )
            }
            if (showProgress) {
                Text(
                    text = progress,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 4.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 4.dp
                            )
                        )
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp)

                )
            }

        }

        Text(
            text = book.name,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp)
        )
        val genre = book.genre.take(2).joinToString(", ")
        Text(
            text = genre,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }

}

@Composable
fun BookCardRow(
    book: Book,
    showRating: Boolean = false,
    showView: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.image)
                .crossfade(true)
                .placeholder(R.drawable.placeholder1)
                .error(R.drawable.placeholder1)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.FillBounds,
        )
        //Spacer(modifier.width(10.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp)
        ) {
            Text(
                text = book.name,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
            val genre = book.genre.take(2).joinToString(", ")
            Text(
                text = genre,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
            )
            if (showView) {
                val view = book.view.toString() + " Views"
                Text(
                    text = view,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }
            if (showRating) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                            .padding(top = 4.dp)
                ) {
                    Text(
                        text = "" + book.rating,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.baseline_star_24),
                        contentDescription = null,
                        tint = Color(0xFFFFB74D),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 5.dp),
                    )
                }
            }
        }
    }
}


