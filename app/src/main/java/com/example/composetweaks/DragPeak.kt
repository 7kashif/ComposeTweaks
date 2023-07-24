package com.example.composetweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.composetweaks.ui.theme.Orange
import kotlinx.coroutines.launch
import kotlin.math.floor

private val alphabets: List<String> = ('A'..'Z').map { it.toString() }

@Composable
fun DragPeak() {

    val peakOffSet by remember {
        mutableStateOf((-75).dp)
    }

    var selectedAlphabet by remember {
        mutableStateOf("")
    }

    var itemSize by remember {
        mutableStateOf(0f)
    }

    val offSets = remember {
        mutableStateListOf<Dp>().apply {
            repeat(alphabets.size) {
                add((0).dp)
            }
        }
    }

    var listIndex by remember {
        mutableStateOf(0)
    }

    val filteredList = remember {
        mutableStateListOf<String>().apply { addAll(namesList) }
    }

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
                .pointerInput(Unit) {
                    this.detectVerticalDragGestures(onDragEnd = {
                        scope.launch {
                            selectedAlphabet = ""
                            offSets.replaceAll { 0.dp }
                            filteredList.clear()
                            filteredList.addAll(namesList)
                            listState.scrollToItem(listIndex)
                        }
                    }) { change, _ ->
                        scope.launch {
                            val selectedPosition = change.position.y / itemSize
                            val selectedIndex = floor(selectedPosition)
                                .coerceIn(0f, (alphabets.size - 1).toFloat())
                                .toInt()

                            selectedAlphabet = alphabets[selectedIndex]
                            listIndex = (namesList.indexOfFirst {
                                it.first() == selectedAlphabet.first()
                            })

                            if (selectedIndex < 5) {
                                repeat(5) {
                                    val num = it + 1
                                    offSets[selectedIndex + num] = peakOffSet + ((it + 1) * 15.dp)
                                }
                                repeat(selectedIndex + 1) {
                                    offSets[selectedIndex - it] = peakOffSet + (it * 15.dp)
                                }
                            } else if (selectedIndex > alphabets.size - 6) {
                                repeat(6) {
                                    offSets[selectedIndex - it] = peakOffSet + (it * 15.dp)
                                }
                                val diff = (alphabets.size - 1) - selectedIndex
                                repeat(diff) {
                                    offSets[selectedIndex + it + 1] =
                                        peakOffSet + ((it + 1) * 15.dp)
                                }
                            } else {
                                repeat(6) {
                                    offSets[selectedIndex - it] = peakOffSet + (it * 15.dp)
                                }
                                repeat(5) {
                                    val num = it + 1
                                    offSets[selectedIndex + num] = peakOffSet + ((it + 1) * 15.dp)
                                }
                            }

                            filteredList.clear()
                            filteredList.addAll(
                                namesList.filter { it.first() == selectedAlphabet.first() }
                            )

                        }
                    }
                }, horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center
        ) {
            alphabets.forEachIndexed { index, item ->
                Text(
                    text = item,
                    style = TextStyle(
                        color = if (item == selectedAlphabet) Orange else Color.White,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .offset(x = offSets[index])
                        .graphicsLayer {
                            itemSize = this.size.height
                        }
                )
            }
        }

        SegmentedLazyColumn(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 32.dp),
            segmentBg = Color.Transparent,
            itemBg = Color.Transparent,
            listOfItems = filteredList,
            listState = listState
        )
    }
}

fun List<String>.coercedIndexes(from: Int, to: Int): List<Int> {
    val coercedFrom = from.coerceIn(0, this.size - 1)
    val coercedTo = to.coerceIn(0, this.size - 1)

    return (coercedFrom..coercedTo).toList()
}