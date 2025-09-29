package com.nxt.katalisreading.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.NewBookSection
import com.nxt.katalisreading.domain.model.Section
import com.nxt.katalisreading.presentation.component.BookCardColumn
import com.nxt.katalisreading.presentation.component.BookCardRow
import com.nxt.katalisreading.presentation.component.Loading
import com.nxt.katalisreading.presentation.component.Logo
import com.nxt.katalisreading.presentation.component.Reload
import com.nxt.katalisreading.presentation.component.ShimmerBox
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val state by homeViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    //an hien top bar
    var previousScrollOffset by remember { mutableStateOf(0) }
    val isTopBarVisible by remember {
        derivedStateOf {
            val currentOffset = listState.firstVisibleItemScrollOffset
            val isScrollingDown = currentOffset < previousScrollOffset
            previousScrollOffset = currentOffset
            isScrollingDown || listState.firstVisibleItemIndex == 0
        }
    }


    LaunchedEffect(state.user) {
        if (state.user != null) {
            if (state.user!!.isBeginner) {
                navController.navigate("beginner") {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        homeViewModel.loadDataInit()
        homeViewModel.loadBanner()
        homeViewModel.loadBooksInSection()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = {
            homeViewModel.refresh()
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //Top bar
            AnimatedVisibility(visible = isTopBarVisible) {
                TopBar()
            }

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth()
            ) {

                item {
                    BannerComponent(
                        navController = navController,
                        isLoading = state.bannerLoading,
                        banners = state.banners
                    )
                }
                items(state.sections, key = { it.title }) { item ->
                    HomeSection(bookSection = item, navController)
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }

    Loading(
        isLoading = state.isLoading,
        text = "Đang tải",
        showText = true,
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.35f))
    )
    Reload(
        isReload = state.isReload,
        text = "Tải lại",
        showText = true,
        onClick = {
            homeViewModel.reload()
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (logo, search, notify) = createRefs()
        Logo(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )

        //Icon search
        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(search) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(notify.start, margin = 4.dp)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        //Icon notify
        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(notify) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.notification),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}

@Composable
fun BannerComponent(
    navController: NavController,
    banners: List<Banner> = emptyList(),
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {

    if (isLoading) {
        ShimmerBox(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .padding(top = 10.dp)
        )
    } else {
        val loopBanners = List(3) { banners }.flatten()
        val pagerState = rememberPagerState(initialPage = banners.size) { loopBanners.size }
        // Auto-scroll
        var key by remember { mutableStateOf(false) }
        LaunchedEffect(key) {
            if (pagerState.isScrollInProgress) {
                delay(3000)
                key = !key
            } else {
                delay(5000)



                if (pagerState.currentPage >= banners.size && pagerState.currentPage < banners.size * 2) {
                    val target = (pagerState.currentPage + 1) % loopBanners.size
                    try {
                        pagerState.animateScrollToPage(
                            page = target,
                            animationSpec = tween(durationMillis = 1000)
                        ) //Broken
                    } catch (e: CancellationException) {
                        println("Animation bị hủy: ${e.message}")
                    } finally {
                        key = !key
                    }
                } else {
                    key = !key
                }
            }

        }

        LaunchedEffect(key) {
            snapshotFlow { pagerState.isScrollInProgress }
                .collect { isScrolling ->
                    if (!isScrolling) {
                        if (pagerState.currentPage < banners.size) pagerState.scrollToPage(
                            pagerState.currentPage + banners.size
                        )
                        else if (pagerState.currentPage >= banners.size * 2) pagerState.scrollToPage(
                            pagerState.currentPage - banners.size
                        )
                    }
                }
        }


        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .padding(top = 10.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSpacing = 20.dp,

                ) { page ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clickable(onClick = {
                            navController.navigate("booklist/${loopBanners[page].bookId}") {
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(loopBanners[page].image)
                            .crossfade(true)
                            .placeholder(R.drawable.placeholder1)
                            .error(R.drawable.placeholder1)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }


            }
        }
    }
}

@Composable
fun HomeSection(
    bookSection: Section,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = bookSection.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Thêm >",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(
                        onClick = {
                            navController.navigate("booklist/${bookSection.id}") {
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
            )
        }
        //Neu list book rong thi hien thi 3 o loading
        if (bookSection.listBook.isEmpty()) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                repeat(3) { j ->
                    ShimmerBox(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                            .aspectRatio(3f / 4f)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

            }
        } else if ( bookSection.type == 2 && bookSection.listBook.size >= 6) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                repeat(2) { i ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        repeat(3) { j ->
                            val item = bookSection.listBook.get(3 * i + j)
                            BookCardColumn(
                                book = item,
                                showType = false,
                                showProgress = false,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp)
                                    .clickable(onClick = {
                                        navController.navigate("book_detail/${item.id}") {
                                            popUpTo(navController.graph.findStartDestination().id){
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    })
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                    }
                }
            }
        } else if (bookSection.type == 1 && bookSection.listBook.size >= 15) {
            val chunkedBooks = bookSection.listBook.chunked(3)

            LazyRow(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .padding(top = 12.dp)
            ) {
                items(chunkedBooks) { bookColumn ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        bookColumn.forEach { book ->
                            BookCardRow(
                                book,
                                showRating = true,
                                showView = true,
                                modifier = Modifier
                                    .height(100.dp)
                                    .aspectRatio(3f / 1f)
                                    .clickable(
                                        onClick = {
                                        navController.navigate("book_detail/${book.id}") {
                                            popUpTo(navController.graph.findStartDestination().id){
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    })
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        } else {
            LazyRow(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 12.dp)
            ) {
                items(bookSection.listBook) { item ->

                    BookCardColumn(
                        book = item,
                        showType = false,
                        showProgress = false,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(start = 16.dp)
                            .clickable(
                                onClick = {
                                    navController.navigate("book_detail/${item.id}") {
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    )
                }
                item { Spacer(modifier = Modifier.width(16.dp)) }

            }
        }
    }
}