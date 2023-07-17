package com.example.composetweaks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

@Composable
fun GeneralPlaceHolder() {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .placeholder(
                visible = true,
                color = Color.LightGray,
                highlight = PlaceholderHighlight.shimmer()
            )
    ) {
       RatingBarEditable(onValueChange = {})
    }

}