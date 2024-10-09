package com.example.composetweaks

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.ComposeTweaksTheme
import com.example.composetweaks.ui.theme.Green
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                ZoomingDotsAnimation()
            }
        }
    }

    //To force app to not get effected by increase in system font or display size.
    override fun attachBaseContext(newBase: Context) {
        val configuration = newBase.resources.configuration
        val displayMetrics = newBase.resources.displayMetrics
        if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
            // Current density is different from Default Density. Override it
            configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
        }
        configuration.fontScale = 1.0f
        val newContext = newBase.createConfigurationContext(configuration)
        super.attachBaseContext(newContext)
    }

}

@Composable
fun WalkthroughTestComposable() {
    var namePositionCords by remember {
        mutableStateOf<LayoutCoordinates?>(null)
    }
    var showOverLay by remember {
        mutableStateOf(true)
    }

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(32.dp)
        ) {
            items(20) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.DarkGray, RectangleShape)
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Item $index",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .onGloballyPositioned {
                                println("Global position of item $index in root is ${it.positionInRoot()} & ${it.boundsInRoot()} and in parent is ${it.positionInParent()} & ${it.boundsInParent()}")

                                if (index == 9)
                                    namePositionCords = it
                            }
                    )
                }
            }
        }

        if (showOverLay && namePositionCords != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable {
                        showOverLay = false
                    }
            ) {
                Text(
                    text = "Item 9",
                    style = MaterialTheme.typography.h5.copy(color = Color.White),
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                namePositionCords!!.positionInRoot().x.toInt(),
                                namePositionCords!!.positionInRoot().y.toInt()
                            )
                        }
                )

            }
        }
    }
}
