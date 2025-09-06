package com.nxt.katalisreading.presentation.screen.bookdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.component.ButtonComponent
import com.nxt.katalisreading.presentation.theme.MyAppTheme


@Composable
fun BoolDetailScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                //Top bar
                TopBarBookDetail(modifier = Modifier.padding(top = 10.dp, bottom = 16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    //Name

                    Text(
                        text = "Gả Nhầm Hào Môn, Chủ Mẫu Khó Đương Gả Nhầm Hào Môn, Chủ Mẫu Khó Đương",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 26.sp,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 10.dp, bottom = 20.dp)
                    )


                    BookDetailCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 10.dp, bottom = 20.dp)
                    )

                    //tab
                    val tabs = listOf("Giới thiệu", "Chương", "Đánh giá")
                    var selectedTab by remember { mutableStateOf(0) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            Box(
                                modifier = Modifier
                                    .clickable { selectedTab = index }
                                    .drawBehind {
                                        val strokeWidth = 2.dp.toPx()
                                        val y = size.height - strokeWidth / 2
                                        drawLine(
                                            color = if (selectedTab == index) Color(0xFF948979) else Color.Transparent,
                                            start = Offset(0f, y),
                                            end = Offset(size.width, y),
                                            strokeWidth = strokeWidth
                                        )
                                    }
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tab,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    when (selectedTab) {
                        0 -> Description(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 10.dp)
                        )

                        1 -> Text("Đây là phần Đánh giá")
                        2 -> Text("Đây là phần Nội dung")
                    }

                }
            }

            //Bottom Bar
            Surface(
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    ButtonComponent(
                        text = "Bắt đầu đọc",
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun TopBarBookDetail(
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()

    ) {
        val (backBtn, title, save, more) = createRefs()
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(28.dp)
                .constrainAs(backBtn) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize()
            )
        }
        var showText by remember { mutableStateOf(false) }
        if (showText) {
            Text(
                text = "Gả Nhầm Hào Môn, Chủ Mẫu Khó Đương Gả Nhầm Hào Môn, Chủ Mẫu Khó Đương",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(backBtn.end, 10.dp)
                        end.linkTo(save.start, 40.dp)
                        width = Dimension.fillToConstraints
                    }
            )
        }
        var isSelected by remember { mutableStateOf(false) }
        IconToggleButton(
            checked = isSelected,
            onCheckedChange = {},
            modifier = Modifier
                .size(28.dp)
                .constrainAs(save) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(more.start, 14.dp)
                }
        ) {
            Icon(
                painter = painterResource(
                    if (isSelected) R.drawable.star_selected
                    else R.drawable.star
                ),
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize()
            )
        }

        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .size(28.dp)
                .constrainAs(more) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, 10.dp)
                }
        ) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .size(28.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.more),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize()

                )
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = MaterialTheme.colorScheme.errorContainer,
                tonalElevation = 8.dp
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Chia sẻ",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Tải về",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = " Báo cáo",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }


    }
}


@Composable
fun BookDetailCard(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("")
                .crossfade(true)
                .placeholder(R.drawable.placeholder1)
                .error(R.drawable.placeholder1)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Tác giả",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Truong vip pro",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Thể loại",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            val genres =
                listOf("Action", "Drama", "Mecha", "Mystery", "Romance", "Sci-fi", "Super Natural")
            val annotatedGenres = buildAnnotatedString {
                genres.forEachIndexed { index, genre ->
                    append(genre)
                    if (index < genres.lastIndex) {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Light)
                        ) {
                            append(" | ")
                        }
                    }
                }
            }
            Text(
                text = annotatedGenres,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp)
            )

            Text(
                text = "Lượt đọc",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "120M+",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp)
            )

            Text(
                text = "Đánh giá",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(
                    text = "6.5",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_star_24),
                    contentDescription = null,
                    tint = Color(0xFFFFB74D),
                    modifier = Modifier
                        .size(16.dp)
                )
            }

            Text(
                text = "Ngày phát hành",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "1 / 1 / 2000",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, bottom = 10.dp)
            )
        }
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier
) {
    val des =
        "Một sơ cấp mỹ dung ở cổ đại một sớm xuyên qua, trở thành một nông phụ bị bán cho hán tử ở nông thôn.\n" +
                "\n" +
                "Nhìn căn nhà cỏ lung lay sắp đổ, trượng phu chân bị tàn tật, còn có đứa trẻ gầy trơ xương, nông phụ thở dài, phải cố gắng làm giàu mới được..."
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                text = des,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }

}

@Composable
fun Chapter(
    modifier: Modifier = Modifier
){

}

@Preview(showBackground = true)
@Composable
fun BookDetailPreview() {
    MyAppTheme {
        BoolDetailScreen()
    }
}