package com.example.composetweaks

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedFAB(
    itemsList: List<Pair<String, Int>> = listOf(
        Pair("Heart", R.drawable.heart),
        Pair("Clap", R.drawable.clap),
        Pair("Like", R.drawable.like),
        Pair("Curios", R.drawable.curious),
    ),
    fabItem: Pair<String, Int> = Pair("Support", R.drawable.support),
) {
    var isFabExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Column(
                modifier = Modifier.background(Color.LightGray, RoundedCornerShape(12.dp))
            ) {
                AnimatedContent(
                    targetState = isFabExpanded,
                    label = "fab",
                    transitionSpec = {
                        (expandHorizontally() + expandVertically()).togetherWith(fadeOut())
                    }
                ) {
                    if(it) {
                        Column {
                            itemsList.forEach { item ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = item.second),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        item.first,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        isFabExpanded = !isFabExpanded
                    },
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = fabItem.second),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        AnimatedVisibility(
                            isFabExpanded,
                            enter = expandHorizontally(tween(300)),
                            exit = shrinkHorizontally(tween(300))
                        ) {
                            Text(
                                fabItem.first,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    ) {
        it.hashCode()
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Hello World!")
        }
    }

}