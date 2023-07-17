package com.example.composetweaks

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    checkedBgColor: Color = Color.DarkGray,
    unCheckedBgColor: Color = Color.White,
    borderColor: Color = Color.DarkGray,
    @DrawableRes icon: Int = R.drawable.ic_check,
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onCheckedChange(!checked)
            }
            .run {
                if (checked) {
                    background(color = checkedBgColor)
                } else {
                    background(color = unCheckedBgColor)
                    border(width = 1.5.dp, color = borderColor, shape = RoundedCornerShape(6.dp))
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column {
            AnimatedVisibility(visible = checked) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize().padding(6.dp)
                )
            }
        }
    }
}