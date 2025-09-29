package com.nxt.katalisreading.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nxt.katalisreading.presentation.theme.MyAppTheme
import kotlin.math.ceil
import kotlin.math.floor

val listMMM = listOf(20,10,20,30,40)

@Composable
fun RatingSummary(
    ratingCounts: List<Int>,
    modifier: Modifier = Modifier
){
    var avg = 0f
    var totalRatings = 0
    for(i in 0..4){
        avg += ratingCounts[i]*(i+1)
        totalRatings += ratingCounts[i]
    }
    avg = avg / totalRatings
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Bên trái: điểm + sao
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = String.format("%.1f", avg),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            StarRating(rating = avg)
            Text(
                text = "$totalRatings đánh giá",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 10.dp )
                )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Bên phải: phân phối số sao
        Column(modifier = Modifier.weight(2f)) {
            val maxCount = ratingCounts.maxOrNull()?.takeIf { it > 0 } ?: 1
            for (i in 5 downTo 1) {
                val count = ratingCounts[i-1]
                val ratio = count.toFloat() / totalRatings

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text("$i", fontSize = 14.sp, modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                    RatingBar(
                        ratio = ratio,
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .padding(horizontal = 8.dp),
                        color = Color(0xFFFFC107),
                    )
                }
            }
        }
    }
}

@Composable
fun StarRating(
    rating: Float,        // điểm trung bình (0.0 - 5.0)
    modifier: Modifier = Modifier,
    starSize: Dp = 18.dp,
    space: Dp = 2.dp
) {
    val ratio = rating - floor(rating)
    Row(modifier = modifier) {
        for(i in 1..5){
            if(floor(rating) >= i){
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(starSize)
                )
            }else if(floor(rating).toInt() +1 == i){
                Box(modifier = Modifier.size(starSize)){
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFC7C7C7),
                        modifier = Modifier.matchParentSize()
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier
                            .matchParentSize()
                            .graphicsLayer {
                                clip = true
                                shape = RectangleShape
                            }
                            .drawWithContent {
                                clipRect(right = size.width * ratio) {
                                    this@drawWithContent.drawContent()
                                }
                            }
                    )
                }
            }else{
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFC7C7C7),
                    modifier = Modifier.size(starSize)
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    ratio: Float,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFFFFC107),
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.3f)
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50)) // bo tròn
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(ratio)
                .clip(RoundedCornerShape(50))
                .background(color)
        )
    }
}
