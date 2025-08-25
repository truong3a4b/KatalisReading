package com.nxt.katalisreading.presentation.screen.home

import android.graphics.drawable.Icon
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nxt.katalisreading.R
import com.nxt.katalisreading.domain.model.Banner
import com.nxt.katalisreading.domain.model.BookFetchData
import com.nxt.katalisreading.domain.model.BookSection
import com.nxt.katalisreading.domain.model.HomeSectionFetchData
import com.nxt.katalisreading.presentation.component.BookCardColumn
import com.nxt.katalisreading.presentation.component.Logo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){

    val state by homeViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(state.user) {
        if(state.user != null){
            if(state.user!!.beginner == true){
                navController.navigate("beginner"){
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        }
    }

    LaunchedEffect(state.banners) {
        homeViewModel.loadBanner()
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisible = visibleItems.lastOrNull()?.index ?: 0
                if (lastVisible >= state.sections.size - 3 && state.sections.size < 20) {
                    homeViewModel.loadSection()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //Top bar

        item {
            TopBar()
        }
        item {
            BannerComponent(state.banners)
        }
        items (state.sections){item ->
            HomeSection(item)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TopBar(modifier: Modifier = Modifier){
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (logo, search, notify) =  createRefs()
        Logo(
            modifier = Modifier.constrainAs(logo) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(search){
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

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(notify){
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

@Preview(showBackground = true)
@Composable
fun BannerComponent(
    banners:List<Banner> = listOf(Banner()),
    modifier: Modifier = Modifier
){

    if(banners.isEmpty()) return
    val loopBanners = List(3){banners}.flatten()
    val pagerState = rememberPagerState(initialPage = banners.size){loopBanners.size}
    // Auto-scroll
    var key by remember { mutableStateOf(false) }
    LaunchedEffect(key) {
        if(pagerState.isScrollInProgress) {
            delay(3000)
            key = !key
        }else{
            delay(5000)



            if(pagerState.currentPage >= banners.size && pagerState.currentPage < banners.size*2){
                val target = (pagerState.currentPage + 1) % loopBanners.size
                try{
                    pagerState.animateScrollToPage(page = target, animationSpec = tween(durationMillis = 1000)) //Broken
                } catch (e : CancellationException){
                    println("Animation bị hủy: ${e.message}")
                } finally {
                    key = !key
                }
            }else{
                key = !key
            }
        }

    }

    LaunchedEffect(key) {
        snapshotFlow { pagerState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    if(pagerState.currentPage < banners.size) pagerState.scrollToPage(pagerState.currentPage + banners.size)
                    else if( pagerState.currentPage >= banners.size*2) pagerState.scrollToPage(pagerState.currentPage - banners.size)
                }
            }
    }


    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f/9f)
            .padding(top =10.dp)
    ){
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSpacing = 20.dp,

            ) { page ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
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
                        .clickable(
                            onClick = {}
                        )
                )
            }


        }
    }

}

@Composable
fun HomeSection(bookSection: BookSection){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ){
        Row(){
            Text(
                text = bookSection.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
            Spacer( modifier = Modifier.weight(1f))
            Text(
                text = "Thêm >",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable(
                        onClick = {}
                    )
            )
        }

        if(bookSection.listBook.size == 6){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                repeat(2){i ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                    ){
                        repeat(3){j ->
                            val item = bookSection.listBook.get(3*i+j)
                            BookCardColumn(
                                book = item,
                                showType = false,
                                showProgress = false,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                    }
                }
            }
        } else if(bookSection.listBook.size >= 15){

        }
        else {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
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
                    )
                }
                item { Spacer(modifier = Modifier.width(16.dp)) }

            }
        }
    }
}