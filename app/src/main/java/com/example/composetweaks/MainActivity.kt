package com.example.composetweaks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composetweaks.ui.theme.ComposeTweaksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                FlashLight()
            }
        }
    }
}

