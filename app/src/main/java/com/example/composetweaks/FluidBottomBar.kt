package com.example.composetweaks

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Green
import kotlinx.coroutines.launch

@Composable
fun FluidBottomBar(
    icons: List<Int> = listOf(
        R.drawable.ic_home,
        R.drawable.ic_explore,
        R.drawable.ic_movies,
        R.drawable.ic_profile
    )
) {
    val pagerState = rememberPagerState {
        icons.size
    }

    val scope = rememberCoroutineScope()

    val density = LocalDensity.current
    var startOffSet by remember {
        mutableFloatStateOf(0f)
    }

    val width  = LocalConfiguration.current.screenWidthDp

    val animateStartOffSet by animateFloatAsState(
        targetValue = startOffSet,
        label = "",
        animationSpec = tween(1000)
    )

    val offSetList = List(icons.size) { index ->
        animateIntAsState(
            targetValue = if (startOffSet == index * 0.25f) ((-16).dp.value * density.density).toInt() else 0,
            label = "animateOffSet",
            animationSpec = tween(1000)
        )
    }
    val colorsList = List(icons.size) { index ->
        animateColorAsState(
            targetValue = if (startOffSet == index * 0.25f) Color.White else Color.Black,
            label = "animateOffSet",
            animationSpec = tween(1000)
        )
    }

    var animateCircle by remember { mutableStateOf(false) }
    val animatedYOffset by animateDpAsState(
        targetValue = if (animateCircle) (-24).dp else 12.dp,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "yOffsetAnimation",
        finishedListener = {
            animateCircle = false
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val path = Path().apply {
                            lineTo(size.width * animateStartOffSet, 0f)
                            cubicTo(
                                x3 = (size.width * animateStartOffSet) + size.width * 0.125f,
                                y3 = size.height * 0.7f,
                                x1 = (size.width * animateStartOffSet) + size.width * 0.0625f,
                                y1 = size.height * 0.001f,
                                x2 = (size.width * animateStartOffSet) + size.width * 0.0625f,
                                y2 = size.height * 0.7f
                            )
                            cubicTo(
                                x3 = (size.width * animateStartOffSet) + size.width * 0.25f,
                                y3 = 0f,
                                x1 = (size.width * (animateStartOffSet)) + size.width * 0.1875f,
                                y1 = size.height * 0.7f,
                                x2 = (size.width * (animateStartOffSet)) + size.width * 0.1875f,
                                y2 = size.height * 0.001f
                            )
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color.LightGray
                        )
                        drawCircle(
                            color = Green,
                            radius = 20.dp.toPx(),
                            center = Offset(((size.width * animateStartOffSet)) + (size.width * 0.125f), animatedYOffset.toPx())
                        )
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    icons.forEachIndexed { index, it ->
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "Icon",
                            modifier = Modifier
                                .weight(1f)
                                .offset {
                                    IntOffset(x = 0, y = offSetList[index].value)
                                }
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    animateCircle = true
                                    startOffSet = index * 0.25f
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                                .padding(vertical = 16.dp),
                            tint = colorsList[index].value
                        )
                    }
                }
            }
        }
    ) {
        it.hashCode()

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icons[it]),
                    contentDescription = "Icon",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}