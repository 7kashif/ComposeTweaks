package com.example.composetweaks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val namesList = mutableListOf(
    "Alice", "Alex", "Anna",
    "Bob", "Benjamin", "Bella",
    "Catherine", "Charlie", "Chloe",
    "David", "Daniel", "Diana",
    "Emily", "Ethan", "Ella",
    "Frank", "Fiona", "Freya",
    "George", "Grace", "Gabriel",
    "Henry", "Hannah", "Harrison",
    "Isabella", "Isaac", "Ivy",
    "John", "Jack", "Julia",
    "Kevin", "Katherine", "Kyle",
    "Liam", "Lily", "Lucas",
    "Michael", "Mia", "Matthew",
    "Noah", "Nora", "Nathan",
    "Oliver", "Olivia", "Oscar",
    "Peter", "Penelope", "Patrick",
    "Quentin", "Quinn", "Queenie",
    "Robert", "Rachel", "Ryan",
    "Samuel", "Sophia", "Sebastian",
    "Thomas", "Taylor", "Trinity",
    "Uma", "Uriel", "Ulysses",
    "Vincent", "Victoria", "Valentina",
    "William", "Willow", "Winston",
    "Xavier", "Xena", "Xander",
    "Yasmine", "Yusuf", "Yara",
    "Zachary", "Zoe", "Zane"
)


@Composable
fun SegmentedLazyColumn() {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        itemsIndexed(namesList) { index, item ->
            if(index == 0)
                SegmentItem(alphabet = item.first().toString())
            else if (namesList[index].first() != namesList[index - 1].first())
                SegmentItem(alphabet = item.first().toString())

            NameItem(name = item)
        }
    }

}

@Composable
fun SegmentItem(alphabet: String) {
    Row(
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = alphabet,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun NameItem(name: String) {
    Row(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}