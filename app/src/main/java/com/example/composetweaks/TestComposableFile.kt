package com.example.composetweaks

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedBarsScaffold() {
    val listState = rememberLazyListState()

    var scrollingBackwards by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(listState.lastScrolledForward, listState.lastScrolledBackward) {
        if (scrollingBackwards || listState.lastScrolledBackward)
            scrollingBackwards = !(listState.lastScrolledForward && scrollingBackwards)
    }

    val bottomBarOffSet by animateDpAsState(
        targetValue = if (scrollingBackwards) 0.dp else 56.dp,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "TopAppBar Offset"
    )

    val topBarSize by animateDpAsState(
        targetValue = if (scrollingBackwards) 56.dp else 0.dp,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "TopAppBar Offset"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().height(topBarSize).background(MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top App Bar"
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = 0.dp, y = bottomBarOffSet)
            ) {
                repeat(4) {
                    Text(
                        text = "Item $it",
                        style = MaterialTheme.typography.h6.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    ) {
        it.hashCode()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(200) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Item $it",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }

    }
}


@Composable
fun WalkthroughTestComposable() {
    val offsets = remember {
        mutableStateListOf(*Array<Offset?>(20) { null })
    }

    val rects = remember {
        mutableStateListOf(*Array<Rect?>(20) { null })
    }

    var itemIndex by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    val density = LocalDensity.current

    var itemText by remember {
        mutableStateOf("Item 0")
    }

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(32.dp),
            state = state
        ) {
            items(20) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            offsets[index] = it.positionInRoot()
                            rects[index] = it.boundsInRoot()
                        }
                        .border(1.dp, Color.DarkGray, RectangleShape)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Item $index",
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
                .clickable {
                    scope.launch {
                        state.animateScrollToItem(15)
                        delay(200)
                        itemIndex = 15
                        itemText = "item 15"
                    }
                }
        ) {
            offsets[itemIndex]?.let { offset ->

                val sizeDp = remember {
                    with(density) {
                        DpSize(
                            width = rects[itemIndex]?.size?.width?.toDp() ?: 0.dp,
                            height = rects[itemIndex]?.size?.height?.toDp() ?: 0.dp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                offset.x.toInt(),
                                offset.y.toInt()
                            )
                        }
                        .size(sizeDp)
                        .border(1.dp, Color.Black, RectangleShape)
                        .background(Color.White)
                        .padding(24.dp)
                ) {
                    Text(
                        text = itemText,
                        style = MaterialTheme.typography.h5.copy(color = Color.Black),
                        modifier = Modifier
                    )
                }
            }
        }
    }
}