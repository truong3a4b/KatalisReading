package com.nxt.katalisreading.presentation.screen.folder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Book
import com.nxt.katalisreading.domain.model.Review
import com.nxt.katalisreading.presentation.component.BookCardColumn
import com.nxt.katalisreading.presentation.component.SearchBar
import com.nxt.katalisreading.presentation.theme.MyAppTheme

@Composable
fun FolderScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Thư viện",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 12.dp, start = 16.dp)
        )
        SearchBar(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )

        ) {
            ConstraintLayout(
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                val (icon, title, num, next) = createRefs()
                Icon(
                    painter = painterResource(R.drawable.star_selected),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(icon){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start,16.dp)
                        }
                )
                Text(
                    text = "Yêu thích",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.constrainAs(title){
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(icon.end, 16.dp)
                    }
                )
                Text(
                    text = "14 Truyện",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.constrainAs(num){
                        top.linkTo(title.bottom, 6.dp)
                        start.linkTo(icon.end, 16.dp)
                    }
                )
                IconButton(
                    onClick = {/*TODO*/},
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(next){
                            start.linkTo(num.end, 16.dp)
                            end.linkTo(parent.end,16.dp)
                            bottom.linkTo(parent.bottom, 12.dp)
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(Modifier
                .width(2.dp)
                .fillMaxHeight()
                .padding(vertical = 6.dp)
                .background(color = MaterialTheme.colorScheme.secondary))
            ConstraintLayout(
                Modifier.weight(1f)
                    .fillMaxHeight()
            ) {
                val ( icon, title, num, next) = createRefs()
                Icon(
                    painter = painterResource(R.drawable.download),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(icon){
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start,16.dp)
                        }
                )
                Text(
                    text = "Đã tải",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.constrainAs(title){
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(icon.end, 16.dp)
                    }
                )
                Text(
                    text = "6 Truyện",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.constrainAs(num){
                        top.linkTo(title.bottom, 6.dp)
                        start.linkTo(icon.end, 16.dp)
                    }
                )
                IconButton(
                    onClick = {/*TODO*/},
                    modifier = Modifier
                        .size(20.dp)
                        .constrainAs(next){
                            start.linkTo(num.end, 16.dp)
                            end.linkTo(parent.end,16.dp)
                            bottom.linkTo(parent.bottom, 12.dp)
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }


        }


        //List Book
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            items(10) { bookRow ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    repeat(2) {
                        BookCardColumn(
                            book,
                            showType = false,
                            progress = "11 / 23 Chương",
                            showProgress = true,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}


