package com.example.composetweaks

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
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
import kotlinx.coroutines.launch
import kotlin.math.ceil
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
                           val calculateIndex = floor(selectedPosition).coerceIn(0f, 25f).toInt()

                           selectedAlphabet = alphabets[calculateIndex]
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