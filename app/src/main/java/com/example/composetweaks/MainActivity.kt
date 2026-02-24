package com.example.composetweaks

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.ComposeTweaksTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTweaksTheme {
                ExamplesShowcase()
//                WalkthroughTestComposable()
//              Column(
//                  modifier = Modifier.padding(72.dp)
//              ) {
//                  RollingMoonNightToggle()
//                  CloudyNightToggle()
//              }
//                BottomSheetTest()
//                AnimatingArrow()
//                Box(
//                    modifier = Modifier.fillMaxSize().padding(32.dp),
//                    contentAlignment = androidx.compose.ui.Alignment.Center
//                ) {
//                    TestComposable()
//                }
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


data class ExampleItem(
    val name: String,
    val composable: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamplesShowcase() {
    var selectedExample by remember { mutableStateOf<ExampleItem?>(null) }

    val examples = remember {
        listOf(
            ExampleItem("Animated Bars Scaffold") { AnimatedBarsScaffold() },
            ExampleItem("Neon Pulse Animation") { NeonPulseAnimation() },
            ExampleItem("Spin To Win Wheel") { SpinToWinWheel() },
            ExampleItem("Flashing New Animation") { FlashingNewAnimation() },
            ExampleItem("Animated FAB") { AnimatedFAB() },
            ExampleItem("Animated Lazy Column") { AnimatedLazyColumn() },
            ExampleItem("Animated Tick") { AnimatedTick() },
            ExampleItem("Animating Arrow") { AnimatingArrow() },
            ExampleItem("Card Swipe Stack") { CardSwipeStack() },
            ExampleItem("Cloudy Night Toggle") { CloudyNightToggle() },
            ExampleItem("Clubbed Photos") { ClubbedPhotos() },
            ExampleItem("Confetti Animation") { ConfettiAnimation() },
            ExampleItem("Coupled Scroller") { CoupledScroller() },
           ExampleItem("Custom CheckBox") { CustomCheckBox() },
            ExampleItem("Day Night Switch") { DayNightSwitch() },
            ExampleItem("Dot Loader") { DotLoader() },
            ExampleItem("Dragged Scroll List") { DraggedScrollList() },
            ExampleItem("Flash Light") { FlashLight() },
            ExampleItem("Fluid Bottom Bar") { FluidBottomBar() },
            ExampleItem("Follow Path") { FollowPath() },
            ExampleItem("Gallery Screen") { GalleryScreen() },
            ExampleItem("Heart Beat") { HeartBeat() },
            ExampleItem("Highlighted Text Search") { HighlightedTextSearch() },
            ExampleItem("LinkedIn Reaction") { LinkedInReaction() },
            ExampleItem("Liquid Loader") { LiquidLoader() },
            ExampleItem("Mute UnMute Switch") { MuteUnMuteSwitch() },
           ExampleItem("OTP View") { OTPView() },
            ExampleItem("Owl Carousal") { OwlCarousal() },
            ExampleItem("Percentage Gauge") { PercentageGauge() },
            ExampleItem("Play Pause Button") { PlayPauseButton() },
            ExampleItem("Radio Button Group Example") { RadioButtonGroupExample() },
           ExampleItem("Rating Bar Editable") { RatingBarEditable() },
            ExampleItem("Rolling Moon Night Toggle") { RollingMoonNightToggle() },
            ExampleItem("Segmented Cylinder") { SegmentedCylinder() },
            ExampleItem("Segmented Lazy Column") { SegmentedLazyColumn() },
            ExampleItem("Show Hide") { ShowHide() },
            ExampleItem("Text Scroller") { TextScroller() },
            ExampleItem("Walkthrough Test Composable") { WalkthroughTestComposable() },
            ExampleItem("Zooming Dots Animation") { ZoomingDotsAnimation() },
         //   ExampleItem("Swipe To Mark Card") { SwipeToMarkCard() },
        )
    }

    if (selectedExample == null) {
        // Show list of examples
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Compose Tweaks Examples") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                examples.forEach {
                    Button(
                        onClick = { selectedExample = it },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(it.name)

                }
            }}
        }
    } else {
        // Show selected example
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(selectedExample!!.name) },
                    navigationIcon = {
                        TextButton(onClick = { selectedExample = null }) {
                            Text("← Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                selectedExample!!.composable()
            }
        }
    }
}
