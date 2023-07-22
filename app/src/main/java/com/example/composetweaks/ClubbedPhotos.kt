package com.example.composetweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

private val listOfImages = listOf(
    "https://images.unsplash.com/photo-1537420327992-d6e192287183?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwxfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1454789548928-9efd52dc4031?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwyfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1505506874110-6a7a69069a08?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwzfHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1464802686167-b939a6910659?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw0fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1610296669228-602fa827fc1f?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw1fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1608178398319-48f814d0750c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw2fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw3fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1528722828814-77b9b83aafb2?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw4fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1516339901601-2e1b62dc0c45?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHw5fHxzcGFjZXxlbnwwfHx8fDE2OTAwNTczNjh8MA&ixlib=rb-4.0.3&q=80&w=400",
    "https://images.unsplash.com/photo-1506318137071-a8e063b4bec0?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w0Nzg2OTV8MHwxfHNlYXJjaHwxMHx8c3BhY2V8ZW58MHx8fHwxNjkwMDU3MzY4fDA&ixlib=rb-4.0.3&q=80&w=400"
)


@Composable
fun ClubbedPhotos(
    visiblePhotos: Int = 4 //number of images to be displayed before showing +[NUM] More overlay.
) {

    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        state = listState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (listOfImages.size > visiblePhotos) {
            items(visiblePhotos) { count ->
                PhotoItem(
                    uri = listOfImages[count],
                    showOverLay = count == visiblePhotos - 1,
                    num = (listOfImages.size - visiblePhotos) + 1
                )
            }
        } else {
            items(listOfImages) { item ->
                PhotoItem(uri = item)
            }
        }
    }
}


/**
 * io.coil-kt:coil-compose:2.2.2
 *
 * add this library for AsyncImage.
 */
@Composable
fun PhotoItem(uri: String, showOverLay: Boolean = false, num: Int = 0) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.height(150.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(uri).crossfade(true).build(),
            contentDescription = uri,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        if (showOverLay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
            )
            Text(
                text = "+$num More",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        }
    }
}