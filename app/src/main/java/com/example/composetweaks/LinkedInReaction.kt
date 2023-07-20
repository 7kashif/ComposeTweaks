package com.example.composetweaks

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.delay


private val reactions = listOf(
    R.drawable.like,
    R.drawable.heart,
    R.drawable.support,
    R.drawable.clap,
    R.drawable.curious,
    R.drawable.insightful
)
private val SIZE = 48.dp

@Composable
fun LinkedInReaction() {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val animationsToggle = remember {
        mutableStateListOf<Boolean>().apply {
            repeat(reactions.size) {
                add(false)
            }
        }
    }

    val x1 by getAnimationsX(startAnimation = animationsToggle[0], count = 0)
    val x2 by getAnimationsX(startAnimation = animationsToggle[1], count = 1)
    val x3 by getAnimationsX(startAnimation = animationsToggle[2], count = 2)
    val x4 by getAnimationsX(startAnimation = animationsToggle[3], count = 3)
    val x5 by getAnimationsX(startAnimation = animationsToggle[4], count = 4)
    val x6 by getAnimationsX(startAnimation = animationsToggle[5], count = 5)

    val listX = remember {
        mutableStateListOf(x1,x2,x3,x4,x5,x6)
    }

    LaunchedEffect(key1 = startAnimation) {
        if (startAnimation) {
            repeat(reactions.size) { index ->
                animationsToggle[index] = true
                delay(130)
            }
        } else {
            repeat(reactions.size) { index ->
                animationsToggle[index] = false
                delay(100)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            reactions.forEachIndexed { index, item ->
                Image(
                    modifier = Modifier
                        .offset(
                            x = listX[index],
                            y = yOffSets(animationsToggle)[index].value
                        )
                        .size(sizes(animationsToggle)[index].value),
                    painter = painterResource(id = item),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
            Image(
                modifier = Modifier
                    .size(SIZE)
                    .clip(CircleShape)
                    .clickable {
                        startAnimation = !startAnimation
                    },
                painter = painterResource(id = R.drawable.like),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }

}


@Composable
fun getAnimationsX(startAnimation: Boolean, count: Int) = animateDpAsState(
    targetValue = if (startAnimation) (count * SIZE) + (count * 8.dp) else 16.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun xOffSets(animationToggles: List<Boolean>) = List(reactions.size) {
    Log.e("testing", "called again.")
    getAnimationsX(startAnimation = animationToggles[it], count = it)
}

@Composable
fun getAnimationsY(startAnimation: Boolean) = animateDpAsState(
    targetValue = if (startAnimation) -(SIZE + 16.dp) else 16.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun yOffSets(animationToggles: List<Boolean>) = List(reactions.size) {
    getAnimationsY(startAnimation = animationToggles[it])
}

@Composable
fun getAnimatedSize(startAnimation: Boolean) = animateDpAsState(
    targetValue = if (startAnimation) SIZE else 0.dp,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
)

@Composable
fun sizes(animationToggles: List<Boolean>) =  List(reactions.size) {
    getAnimatedSize(startAnimation = animationToggles[it])
}