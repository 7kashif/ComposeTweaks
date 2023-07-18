package com.example.composetweaks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.ComposeTweaksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                val (check, setCheck) = remember {
                    mutableStateOf(false)
                }
                AnimatedList()
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(32.dp),
//                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    item {
//                        RadioButtonGroupExample()
//                    }
//                    item {
//                        CheckBoxWithText(onCheckedChange = setCheck, checked = check, text = "Check this box.")
//                    }
//                    item {
//                        OTPView {}
//                    }
//                    item {
//                        FilledSlider {}
//                    }
//                    item {
//                        RatingBarEditable {}
//                    }
//                    item{
//                        ConfettiAnimation()
//                    }
//                    item {
//                        OwlCarousal()
//                    }
//                }
            }
        }
    }
}
