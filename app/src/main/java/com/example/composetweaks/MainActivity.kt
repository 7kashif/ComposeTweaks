package com.example.composetweaks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.composetweaks.ui.theme.ComposeTweaksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                val (check, setCheck) = remember {
                    mutableStateOf(false)
                }
//                CurvedPathAnimation()
                LinkedInReaction()
//                DragPeak()
//                AnimatedLazyColumn()
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
