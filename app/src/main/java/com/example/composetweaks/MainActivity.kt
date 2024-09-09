package com.example.composetweaks

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetweaks.gl.OpenGLComposable
import com.example.composetweaks.ui.theme.ComposeTweaksTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                FluidBottomBar()
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

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ShaderExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        OpenGLComposable(
            modifier = Modifier.fillMaxSize(),
        )
    }
}


