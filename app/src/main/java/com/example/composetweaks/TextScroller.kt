package com.example.composetweaks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun TextScroller() {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val items = remember {
        listOf(
            "first item",
            "second item",
            "third item",
            "fourth item",
        )
    }

    LaunchedEffect(Unit) {
        while (items.size > 1) {
            delay(2000)
            selectedIndex = (selectedIndex + 1) % items.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = selectedIndex,
            transitionSpec = {
                slideInVertically { it }.togetherWith(slideOutVertically { -it })
            },
            label = ""
        ) {
            Text(
                text = items[it],
                style = MaterialTheme.typography.body2,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}