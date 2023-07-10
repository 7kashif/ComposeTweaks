package com.example.composetweaks

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider() {
    val (value, setValue) = remember {
        mutableStateOf(0f)
    }

    Slider(value = value, onValueChange = setValue ) {

    }


}