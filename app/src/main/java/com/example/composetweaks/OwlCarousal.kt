package com.example.composetweaks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OwlCarousal() {
    val pagerList by remember {
        mutableStateOf(listOf(Color.Black, Color.DarkGray, Color.Gray))
    }

    val pagerState = rememberPagerState(Int.MAX_VALUE / 2)

    HorizontalPager(
        pageCount = Int.MAX_VALUE,
        modifier = Modifier.fillMaxWidth(),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp)
    ) {
        Box(
            modifier = Modifier
                .carouselTransition(it, pagerState)
                .height(200.dp)
                .fillMaxWidth()
                .background(pagerList[it % pagerList.size])
        ){
            //TODO ADD YOUR VIEWS HERE
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
fun Modifier.carouselTransition(page: Int, pagerState: PagerState) =
    graphicsLayer {
        val pageOffset =
            ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

        val transformation =
            lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )
        alpha = transformation
        scaleY = transformation
    }