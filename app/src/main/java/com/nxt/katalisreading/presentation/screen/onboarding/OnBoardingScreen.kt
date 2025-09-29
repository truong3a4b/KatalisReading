package com.nxt.katalisreading.presentation.screen.onboarding

import android.widget.Button
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nxt.katalisreading.R
import com.nxt.katalisreading.presentation.navigation.Screen
import com.nxt.katalisreading.presentation.theme.MyAppTheme



@Composable
fun OnBoardingScreen(navController: NavController){
    val pagerState = rememberPagerState(0) { onboardingPages.size }
    Column(verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background )
            .padding(16.dp)
    ){
        OnBoardingPager(pagerState = pagerState, pages = onboardingPages)
        ButtonColumn(
            signInOnClick = { navController.navigate(Screen.Login.route) },
            guestOnClick = { navController.navigate(Screen.Home.route) }
        )
    }
}

@Composable
fun OnBoardingPager(
    pagerState: PagerState,
    pages: List<OnBoardingPage>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            pageSize = PageSize.Fill,

            ) { page ->
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                ) {
                    Image(painter = painterResource(id = pages[page].image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = pages[page].title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 36.dp)
                    )
                    Text(
                        text = pages[page].description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp)
                    )
            }
        }
    }
    //Indicator
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row (horizontalArrangement = Arrangement.Center) {
            repeat(pages.size) { index ->
                val color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(color, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Spacer(Modifier.width(4.dp))
            }
        }
    }
}

@Composable
fun ButtonColumn(
    signInOnClick : () -> Unit ,
    guestOnClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
        .padding(top = 48.dp,bottom = 8.dp)
    ) {
        //Sign in button
        Button(
            onClick = { signInOnClick() },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Sign in",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium
            )
        }

        //Guest button
        Button(
            onClick = { guestOnClick },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.primary,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Enter as guest",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

data class OnBoardingPage(
    val image: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnBoardingPage(R.drawable.onboarding_img1, "Start Your Adventure Through Stories", "Discover a world of fantasy, action, and romance in hundreds of favorite novel and comic titles. One chapter can open up a whole new world!"),
    OnBoardingPage(R.drawable.onboarding_img2, "The more you read,the more you get!", "Every page you read can pave the way to exciting rewards. Keep reading, because every word you skip means something!"),
    OnBoardingPage(R.drawable.onboarding_img3, "Find a Story that Fits You", "From heartwarming romances to adrenaline-pumping adventures-explore a variety of genres and find the story you love the most!")
)