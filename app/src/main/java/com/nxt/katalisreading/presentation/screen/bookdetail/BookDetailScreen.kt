package com.nxt.katalisreading.presentation.screen.bookdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Review
import com.nxt.katalisreading.presentation.component.ButtonComponent
import com.nxt.katalisreading.presentation.component.CommentBox
import com.nxt.katalisreading.presentation.component.CommentCard
import com.nxt.katalisreading.presentation.component.RatingSummary
import com.nxt.katalisreading.presentation.theme.MyAppTheme
import java.util.Collections.emptyList

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    bookId: String,
    bookDetailViewModel: BookDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val state = bookDetailViewModel.state.collectAsState().value

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

                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
//                val totalChapters = 1000
//                val pageSize = 20
//                val totalPages = (totalChapters + pageSize - 1) / pageSize // làm tròn
//                var currentPage by remember { mutableStateOf(1) }
//                val pages = getPageList(currentPage, totalPages, 3)
//                val start = (currentPage - 1) * pageSize + 1
//                val end = minOf(currentPage * pageSize, totalChapters)
//                var currentChapters by remember { mutableStateOf((start..end).toList()) }
//                currentChapters = (start..end).toList()

                val listReview = listOf(Review("1","1","","Nguyen Xuan Truong", 5, "Hay lắm"),
                    Review("1","1","","Nguyen Ho Duong Thang", 1, "Truyện rác"),
                    Review("1","1","","Phạm Quang Huy", 3, "Lắm sạn vl"))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    //Name

                    item {
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
                    }

                    item {
                        BookDetailCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 10.dp, bottom = 20.dp)
                        )
                    }


                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            state.tabs.forEachIndexed { index, tab ->
                                Box(
                                    modifier = Modifier
                                        .clickable { bookDetailViewModel.updateSelectedTab(index) }
                                        .drawBehind {
                                            val strokeWidth = 2.dp.toPx()
                                            val y = size.height - strokeWidth / 2
                                            drawLine(
                                                color = if (state.selectedTab == index) Color(0xFF948979) else Color.Transparent,
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
                                        color = if (state.selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                                        fontWeight = if (state.selectedTab == index) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    when (state.selectedTab) {
                        0 -> item {
                            Description(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 10.dp, top = 16.dp    )
                            )
                        }

                        1 -> {

                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 16.dp)
                                ) {
                                    Text(
                                        text = "Tìm kiếm chương:",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                    )
                                    BasicTextField(
                                        value = state.chapterSearch,
                                        onValueChange = {
                                            bookDetailViewModel.onSearchChange(it.filter { ch -> ch.isDigit() })
                                        }, // chỉ cho nhập số
                                        singleLine = true,
                                        textStyle = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            textAlign = TextAlign.Center
                                        ),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                if (state.chapterSearch.isNotEmpty() && state.chapterSearch.toInt() <= state.totalChapter) {
                                                    currentChapters = listOf(input.toInt())
                                                }else{
                                                    currentChapters = (start..end).toList()
                                                }
                                                keyboardController?.hide()   // ẩn bàn phím
                                                focusManager.clearFocus()
                                            }
                                        ),
                                        modifier = Modifier
                                            .width(60.dp)
                                            .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 8.dp)
                                    )


                                }
                            }
                            items(currentChapters) { chapter ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                end = 10.dp,
                                                top = 8.dp,
                                                bottom = 8.dp
                                            )
                                            .clickable { /* TODO mở chương */ }
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.outline_keyboard_double_arrow_right_24),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "Chương $chapter",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Normal
                                            ),
                                            modifier = Modifier
                                                .padding(start = 10.dp)

                                        )

                                    }
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .background(color = Color(0x60D2D2D2))
                                    )
                                }
                            }

                            item {
                                LazyRow(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    items(pages) { page ->
                                        if (page == null) {
                                            Text(
                                                text = "...",
                                                style = MaterialTheme.typography.bodySmall,
                                                modifier = Modifier
                                                    .border(
                                                        1.dp,
                                                        Color.Gray,
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .padding(8.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))

                                        } else {
                                            Text(
                                                text = page.toString(),
                                                style = MaterialTheme.typography.bodySmall,
                                                fontWeight = if (page == currentPage) FontWeight.Bold else FontWeight.Normal,
                                                color = if (page == currentPage) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
                                                modifier = Modifier
                                                    .clickable {
                                                        currentPage = page
                                                        input = ""
                                                    }
                                                    .border(
                                                        width = 1.dp,
                                                        Color.Gray,
                                                        shape =RoundedCornerShape(4.dp)
                                                    )
                                                    .background(color = if (page == currentPage) MaterialTheme.colorScheme.primary else Color.Transparent,shape =RoundedCornerShape(4.dp))
                                                    .padding(8.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                        }
                                    }
                                }
                            }


                        }

                        2 -> {
                            item { RatingSummary(
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 16.dp, end = 16.dp )
                                    .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                            ) }
                            item {
                                Rating(
                                    modifier = Modifier
                                        .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp )
                                        .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                                )
                            }
                            items(listReview){review ->
                                CommentCard(
                                    avatarRes = review.avatar,
                                    name = review.userName,
                                    time = "2/4/2025",
                                    rating = review.star,
                                    comment = review.comment,
                                    modifier = Modifier
                                        .padding(start = 16.dp,end = 16.dp, bottom = 16.dp)
                                )
                            }
                            item{
                                Text(
                                    text = "Xem tất cả các bài đánh giá",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF108FF5),
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp )
                                        .clickable(onClick = {/*TODO */})
                                )
                            }
                        }
                    }

                    item{
                        Spacer(modifier = Modifier.height(80.dp ))
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(color = MaterialTheme.colorScheme.surface),
                ) {
                    //Guest button
                    Button(
                        onClick = {/*TODO*/  },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.background,
                            disabledContentColor = MaterialTheme.colorScheme.primary,
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Đọc từ đầu",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    ButtonComponent(
                        text = "Tiếp tục đọc",
                        onClick = {/*TODO*/ },
                        modifier = Modifier
                            .weight(1f)
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
                    .clickable(
                        onClick = {}
                    )
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
            onCheckedChange = {
                isSelected = !isSelected
            },
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
                .data("https://lh3.googleusercontent.com/pw/AJFCJaUGi99Cs0UIw6kCh61Vay3CL_wuV_OlIafOlZK_31vhJj1-UIfmbzcXiJ-m6xkpVbyRtNn2mB7KirQFnKu_qpcX0ewej6_lT7o409GZNn-br_-RSAYsv2SDbYgL1GfIC-jLNoQ831-qO-Uqy53-lM-y=w215-h322-s-no?authuser=1")
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
                    text = "3.5",
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

    Text(
        text = des,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )


}

@Composable
fun Rating(
    modifier: Modifier = Modifier
){
    var star by remember { mutableStateOf(0) }
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ){
            for(i in 1 .. 5){
                IconButton(
                    onClick = {
                        if(star == i) star = 0
                        else star = i
                    },

                ) {
                    Icon(
                        painter = painterResource(
                            if (i <= star) R.drawable.star_selected
                            else R.drawable.star
                        ),
                        contentDescription = null,
                        tint = if (i <= star) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
        Text(
            text = "Bạn muốn viết đánh giá cho truyện?",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 10.dp, start = 16.dp, end = 16.dp)
        )
        CommentBox(
            maxLength = 200,
            placeholder = "Viết đánh giá",
        )

    }
}

fun getPageList(current: Int, total: Int, maxVisible: Int = 5): List<Int?> {
    val pages = mutableListOf<Int?>()

    if (total <= maxVisible + 2) {
        // Nếu ít trang thì hiện hết
        (1..total).forEach { pages.add(it) }
    } else {
        if(current == 1) {
            (1..maxVisible).forEach { pages.add(it) }
            pages.add(null)
            pages.add(total)
        }else if(current == total){
            pages.add(1)
            pages.add(null)
            ((total - (maxVisible - 1)) until total).forEach { pages.add(it) }
            pages.add(total)
        }

        // Ở giữa
        else {
            val start = current- maxVisible / 2
            val end = current + maxVisible / 2
            if(start == 1){
                for(i in start .. end){
                    pages.add(i)
                }
                pages.add(null)
                pages.add(total)
            }
            else if(end == total){
                pages.add(1)
                pages.add(null)
                for(i in start .. end){
                    pages.add(i)
                }
            }else{
                pages.add(1)
                if(start != 2) pages.add(null)
                for (i in start .. end) {
                    pages.add(i)
                }
                if(end != total-1) pages.add(null)
                pages.add(total)
            }

        }

    }

    return pages
}
