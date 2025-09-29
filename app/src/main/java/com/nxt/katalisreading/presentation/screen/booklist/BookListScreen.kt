package com.nxt.katalisreading.presentation.screen.booklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.component.BookCardColumn
import com.nxt.katalisreading.presentation.component.Loading
import com.nxt.katalisreading.presentation.screen.home.HomeViewModel

@Composable
fun BookListScreen(
    navController: NavController,
    sectionIndex: Int,
    homeViewModel: HomeViewModel = hiltViewModel(),
    bookListViewModel: BookListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val homeState by homeViewModel.state.collectAsState()
    val bookListState by bookListViewModel.state.collectAsState()
    val section = homeState.sections[sectionIndex]

    LaunchedEffect(Unit) {
        bookListViewModel.updateSection(section)
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //Top bar
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = section.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        //List Book
        val chunkedBook = bookListState.bookList.chunked(2)
        val listState = rememberLazyListState()

        //Luot xuong cuoi thi load them
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                .collect { visibleItems ->
                    val lastVisible = visibleItems.lastOrNull()?.index ?: 0
                    if (lastVisible >= chunkedBook.size-2 && bookListState.allowLoad) {
                        bookListViewModel.loadBook()
                    }
                }
        }
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(chunkedBook) { bookRow ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 10.dp)
                ) {
                    bookRow.forEach { book ->
                        BookCardColumn(
                            book,
                            showType = false,
                            showProgress = false,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    if(bookRow.size == 1){
                        Spacer(
                            modifier = Modifier
                            .weight(1f)
                        )
                    }
                }
            }
            item {
                Loading(
                    isLoading = bookListState.isLoading,
                    showText = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}

