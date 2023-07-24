package com.example.composetweaks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.ComposeTweaksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (value, setValue) = remember {
                mutableStateOf(0f)
            }

            ComposeTweaksTheme {
                DragPeak()
//               Box(
//                   modifier = Modifier.fillMaxSize(),
//                   contentAlignment = Alignment.Center
//               ){
//                   RatingBarEditable(
//                       rating = value,
//                       onValueChange = setValue,
//                       maxRating = 5,
//                       starSize = 32.dp,
//                       isEditable = true,
//                       isDraggable = false,
//                       roundOffToWholeNumber = false,
//                       ratingColor = Color.DarkGray
//                   )
//               }
            }
        }
    }
}

