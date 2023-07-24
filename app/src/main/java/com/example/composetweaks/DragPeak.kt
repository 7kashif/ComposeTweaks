package com.example.composetweaks

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.launch
import kotlin.math.floor

private val alphabets: List<String> = ('a'..'z').map { it.toString() }

@Composable
fun DragPeak() {

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

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .pointerInput(Unit) {
                    this.detectVerticalDragGestures(
                        onDragEnd = {
                           scope.launch {
                               selectedAlphabet = ""
                               offSets.replaceAll { 0.dp }
                           }
                        }
                    ) { change, _ ->
                       scope.launch {
                           val selectedPosition = change.position.y / itemSize
                           val index = floor(selectedPosition).coerceIn(0f, 25f).toInt()

                           selectedAlphabet = alphabets[index]
                           if(index < 5) {

                           } else if(index > alphabets.size - 6) {

                           } else {
                               val offSet = (-75).dp
                               offSets[index] = offSet
                               repeat(5) {
                                   val num = it + 1
                                   offSets[index - num] = offSet + (it * 15.dp)
                               }
                               repeat(5) {
                                   val num = it + 1
                                   offSets[index + num] = offSet + (it * 15.dp)
                               }
                           }
                       }
                    }
                },
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            alphabets.forEachIndexed {index, item ->
                Text(
                    text = item,
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .graphicsLayer {
                            itemSize = this.size.height
                        }
                        .offset(
                            x = offSets[index]
                        )
                )
            }
        }

    }


}