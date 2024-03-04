package com.example.composetweaks

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

data class Item (
    val title: String,
    var offSet: Offset
)

@Composable
fun DragNDropGrid() {
    val items = remember {
        mutableStateListOf(
            "Speedster", "Mountain King", "Urban Explorer", "Trail Blazer", "Velocity",
            "Night Rider", "Highway Hawk", "Canyon Cruiser", "Pavement Pro", "Dirt Destroyer",
            "Sky Jumper", "City Sprinter", "Twilight Tracker", "Eco Rider", "Thunderbolt"
        )
    }
    val offSets = remember { mutableStateListOf(*Array(items.size) { Offset(0f, 0f) } ) }


    val gridState = rememberLazyGridState()
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp

    var swapIndex = 2

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        itemsIndexed(items) { index, item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .offset(offSets[index].x.dp, offSets[index].y.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .height(100.dp)
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                if (offSets[index].y > 70f) {
                                    offSets[index] = Offset(0f, 0f)
                                    offSets[index + 2] = Offset(0f, 0f)
                                    items[index] = items[index + 2].also {
                                        items[index + 2] = items[index]
                                    }
                                    swapIndex = 2
                                }
                            }
                        ) { change, dragAmount ->
                            val dragAmountYInDp = with(density) {
                                dragAmount.y.toDp()
                            }
                            val dragAmountXInDp = with(density) {
                                dragAmount.x.toDp()
                            }


                            offSets[index] = Offset(
                                offSets[index].x + dragAmountXInDp.value,
                                offSets[index].y + dragAmountYInDp.value
                            )
                            offSets[index + swapIndex] = Offset(
                                0f,
                                offSets[index + swapIndex].y - dragAmountYInDp.value
                            )

                            val dragMod = (offSets[index].y.toInt() % 100)


                            if (dragMod == 0 && offSets[index].y > 100f) {
                                swapIndex += 2
                            }

                            Log.e("dragMode", "${offSets[index]}")


                            change.consume()
                        }
                    }
            ) {
                // Your grid item content here, for example:
                Text(text = item, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
