package com.example.composetweaks

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OwlCarousal() {
    val pagerList by remember {
        mutableStateOf(listOf(Color.Black, Color.DarkGray, Color.Gray))
    }
    val heightList = remember {
        MutableList(pagerList.size) {
            if (it == 0)
                240.dp
            else
                220.dp
        }
    }
    val pagerState = rememberPagerState()
    var currentPageIndex by remember {
        mutableStateOf(0)
    }
    var targetPageIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = pagerState.isScrollInProgress) {
        currentPageIndex = pagerState.currentPage
        targetPageIndex = pagerState.targetPage
        Log.e("pagerScroll", "current $currentPageIndex >>> target $targetPageIndex")
    }

    LaunchedEffect(key1 = pagerState.currentPageOffsetFraction) {

        val fraction = abs(
            (pagerState.currentPageOffsetFraction * 100).convertToRange(
                oldRangeStart = 0f,
                oldRangeEnd = 50f,
                newRangeStart = 0f,
                newRangeEnd = 20f
            )
        )

        heightList[currentPageIndex] = 220.dp - fraction.dp
        heightList[targetPageIndex] = 220.dp + fraction.dp
    }

    HorizontalPager(
        pageCount = pagerList.size,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp)
    ) {
        Box(
            modifier = Modifier
                .height(heightList[it])
                .fillMaxWidth()
                .background(pagerList[it])
        )
    }

}