package com.example.composetweaks

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun GalleryScreen() {
    var permissionState by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        permissionState = it
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        } else {
            launcher.launch(
                android.Manifest.permission.READ_MEDIA_IMAGES
            )
        }
    }

    if (permissionState) {
        // Permission granted, show images
        ScrollableDockedContent()
    } else {
        // Handle permission denied case
    }
}

@Composable
fun ScrollableDockedContent() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current
    val gridState = rememberLazyGridState()
    val scope = rememberCoroutineScope()

    val screenHeightPx by remember {
        mutableIntStateOf(
            with(density) {
                screenHeight.dp.toPx().toInt()
            }
        )
    }

    val screen70Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.75).dp.toPx().toInt()
        })
    }
    val screen50Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.5).dp.toPx().toInt()
        })
    }
    val screen60Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.6).dp.toPx().toInt()
        })
    }

    val screen30Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.3).dp.toPx().toInt()
        })
    }
    var offsetY by remember {
        mutableIntStateOf(screen70Perc)
    }

    var canDragDown by remember {
        mutableStateOf(false)
    }

    var shouldAnimate by remember {
        mutableStateOf(false)
    }

    val animOffSetY by animateIntAsState(
        targetValue = offsetY,
        animationSpec = tween(
            durationMillis = if (shouldAnimate) 200 else 0,
            easing = LinearEasing
        ),
        label = ""
    )


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return if (gridState.firstVisibleItemIndex == 0 && available.y < 0 && offsetY > 0) {
                    shouldAnimate = false
                    val delta = available.y.toInt()
                    val newOffSetY = offsetY + delta
                    offsetY = newOffSetY.coerceIn(0, screen70Perc)
                    available
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                return if (gridState.firstVisibleItemIndex == 0 && available.y > 0 && offsetY < screen70Perc && canDragDown) {
                    shouldAnimate = false
                    val delta = available.y.toInt()
                    val newOffSetY = offsetY + delta
                    offsetY = newOffSetY.coerceIn(0, screen70Perc)
                    available
                } else {
                    canDragDown = false
                    Offset.Zero
                }
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (offsetY in screen50Perc..<screen70Perc) {
                    shouldAnimate = true
                    offsetY = screen70Perc
                } else if (offsetY in 0..screen50Perc) {
                    shouldAnimate = true
                    canDragDown = true
                    offsetY = 0
                }
                return Velocity.Zero
            }
        }
    }

    val context = LocalContext.current
    val imageUris = remember {
        mutableStateListOf<Uri>()
    }


    LaunchedEffect(Unit) {
        val temp = fetchGalleryImages(context)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
        imageUris.addAll(temp)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        Column(
            modifier = Modifier
                .offset {
                    IntOffset(0, animOffSetY)
                }
                .safeDrawingPadding()
                .background(Color.Black.copy(alpha = 0.8f))
        ) {
            Text(
                text = "Gallery se select karain!",
                style = MaterialTheme.typography.h6.copy(textAlign = TextAlign.Center),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            shouldAnimate = true
                            offsetY = if (offsetY == 0) {
                                gridState.scrollToItem(0)
                                screen70Perc
                            } else {
                                0
                            }
                        }
                    }
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                shouldAnimate = true
                                offsetY = if (offsetY in screen50Perc..screenHeightPx) {
                                    screen70Perc
                                } else {
                                    0
                                }
                            }
                        ) { _, dragAmount ->
                            shouldAnimate = false
                            offsetY += dragAmount.y.toInt()
                        }
                    }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(imageUris) { uri ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(uri).crossfade(true)
                            .build(),
                        contentDescription = uri.toString(),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }


}

@Composable
fun GalleryGrid() {
    val context = LocalContext.current
    val imageUris = remember {
        mutableStateListOf<Uri>()
    }


    LaunchedEffect(Unit) {
        imageUris.addAll(fetchGalleryImages(context))
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        DraggableDockedContent(
            headerContent = {
                Text(
                    text = "Gallery se select karain!",
                    style = MaterialTheme.typography.h6,
                    color = Color.White
                )
            }
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(imageUris) { uri ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(uri).crossfade(true)
                            .build(),
                        contentDescription = uri.toString(),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }


}

@Composable
fun DraggableDockedContent(
    modifier: Modifier = Modifier,
    backGroundColor: Color = Color.Black.copy(alpha = 0.8f),
    headerContent: @Composable RowScope.() -> Unit,
    bodyContent: @Composable () -> Unit
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    var shouldAnimate by remember {
        mutableStateOf(false)
    }

    val density = LocalDensity.current

    var offsetY by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.75).dp.toPx().toInt()
        })
    }

    val screen70Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.75).dp.toPx().toInt()
        })
    }

    val screen60Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.6).dp.toPx().toInt()
        })
    }

    val screen30Perc by remember {
        mutableIntStateOf(with(density) {
            (screenHeight * 0.3).dp.toPx().toInt()
        })
    }

    val offSetAnim by animateIntAsState(
        targetValue = offsetY,
        animationSpec = tween(
            durationMillis = if (shouldAnimate) 200 else 0,
            easing = LinearEasing
        ),
        label = ""
    )

    Column(
        modifier = modifier
            .offset {
                IntOffset(0, offSetAnim)
            }
            .safeDrawingPadding()
            .fillMaxSize()
            .background(backGroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    shouldAnimate = true
                    offsetY = if (offsetY == screen70Perc) {
                        0
                    } else {
                        screen70Perc
                    }
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            shouldAnimate = true
                            offsetY = if (offsetY > screen60Perc || offsetY < screen30Perc) {
                                with(density) {
                                    (screenHeight * 0.75).dp
                                        .toPx()
                                        .toInt()
                                }
                            } else {
                                0
                            }
                        }
                    ) { _, dragAmount ->
                        shouldAnimate = false
                        offsetY += dragAmount.y.toInt()
                    }
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            headerContent()
        }
        bodyContent()
    }

}

private suspend fun fetchGalleryImages(context: Context): List<Uri> {
    return withContext(Dispatchers.IO) {
        val imageUris = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri =
                    Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                imageUris.add(uri)
            }
        }
        imageUris
    }
}

fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val byteBuffer = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream?.read(buffer).also { len = it ?: -1 } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        inputStream?.close()
        byteBuffer.toByteArray()
    } catch (e: Exception) {
        null
    }
}