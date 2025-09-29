package com.nxt.katalisreading.presentation.screen.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.presentation.component.BookCardRow
import com.nxt.katalisreading.presentation.screen.home.TopBar
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Composable
fun RankingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar()
        val tabs = listOf(
            Pair(1, "Top Lượt Xem"),
            Pair(2, "Top tuần"),
            Pair(3, "Truyện được đánh giá cao"),
            Pair(4, "Truyện được yêu thích")
        )
        var selectedTab by remember { mutableStateOf(1) }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(tabs) { tab ->
                Box(
                    modifier = Modifier
                        .clickable { selectedTab = tab.first }
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                color = if (selectedTab == tab.first) Color(0xFF948979) else Color.Transparent,
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = strokeWidth
                            )
                        }
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.second,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedTab == tab.first) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                        fontWeight = if (selectedTab == tab.first) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
        val book = Book(
            "1",
            "Ta Là Con Rối Vô Địch",
            "https://lh3.googleusercontent.com/pw/AJFCJaUGi99Cs0UIw6kCh61Vay3CL_wuV_OlIafOlZK_31vhJj1-UIfmbzcXiJ-m6xkpVbyRtNn2mB7KirQFnKu_qpcX0ewej6_lT7o409GZNn-br_-RSAYsv2SDbYgL1GfIC-jLNoQ831-qO-Uqy53-lM-y=w215-h322-s-no?authuser=1",
            "novel",
            listOf("Tiên hiệp"),
            "",
            "",
            4.5f,
            listOf(0,0,0,0,0),
            177,
            1756951320077,
            175695132007,
            emptyList(),
            emptyList(),
            10
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 16.dp, end = 10.dp)
        ) {
            items(20) { i ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    val color = if (i == 0) {
                        Color(0xFFFFA200)
                    } else if (i == 1) {
                        Color(0xFFB4B3B3)
                    } else if(i == 2){
                        Color(0xFF793D0C)
                    }else{
                        MaterialTheme.colorScheme.onBackground
                    }
                    Text(
                        text = "${i + 1}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if(i <=2) FontWeight.Bold else FontWeight.Normal,
                        color = color
                    )
                    Spacer(Modifier.width(10.dp))
                    BookCardRow(
                        book,
                        showRating = true,
                        showView = true,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .clickable(onClick = { navController.navigate("book_detail/1") })
                    )
                }

            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RankingScreenPreview() {
    MyAppTheme {
        RankingScreen(
            navController = NavController(LocalContext.current)
        )
    }
}