package com.example.composetweaks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.composetweaks.ui.theme.Green


@Composable
fun HighlightedTextSearch() {
    val listState = rememberLazyListState()

    val mobileAppFacts = remember {
        listOf(
            "The Apple App Store, launched in 2008, was the first digital distribution platform for mobile apps, offering 500 apps at launch.",
            "On average, users spend 90% of their mobile time on apps, with social media and communication apps being the most popular.",
            "As of 2024, Android holds about 71% of the global mobile operating system market share, making it the most widely used mobile OS.",
            "The majority of mobile app revenue comes from in-app purchases and ads, with games being the top revenue generators.",
            "It’s estimated that more than 100,000 new apps are released every month across various platforms, especially on Android and iOS.",
            "Mobile games account for nearly 50% of the total gaming industry revenue, surpassing PC and console gaming combined.",
            "The average smartphone user has over 80 apps installed on their device but typically uses only about 9 apps daily.",
            "The first mobile phone call was made on April 3, 1973, by Martin Cooper, a Motorola researcher and executive.",
            "In 2023, there were over 3.5 billion smartphone users worldwide, which is nearly 45% of the global population.",
            "The most downloaded app of all time is Facebook, with over 5 billion downloads on the Google Play Store alone."
        )
    }

    val filteredList = remember {
        mutableStateListOf("")
    }

    val (query, setQuery) = remember { mutableStateOf("") }

    LaunchedEffect(query) {
        if (query.isNotEmpty()) {
            filteredList.clear()
            filteredList.addAll(
                mobileAppFacts.filter {
                    it.contains(query, ignoreCase = true)
                }
            )
        } else {
            filteredList.clear()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = setQuery,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(filteredList.isNotEmpty() || query.isNotEmpty()) {
                items(filteredList) { item ->
                    HighlightableText(
                        text = item,
                        query = query
                    )
                    Spacer(Modifier.height(8.dp))
                    Divider()
                }
            } else {
                items(mobileAppFacts) { item ->
                    Text(
                        text = item
                    )
                    Spacer(Modifier.height(8.dp))
                    Divider()
                }
            }
        }
    }
}

@Composable
fun HighlightableText(
    text: String,
    query: String
) {
    Text(
        text = buildAnnotatedString {
            text.split(" ").forEach {split ->
                if(split.contains(query, true)) {
                    println("${split.split(query)}")
                    split.splitString(query).forEach {
                        if(it == query) {
                            withStyle(
                                style = SpanStyle(
                                    background = Green
                                )
                            ) {
                                append(it)
                            }
                        } else {
                            append(it)
                        }
                    }
                } else {
                    append(split)
                }

                append(" ")
            }
        }
    )
}

fun String.splitString(subString: String): List<String> {
    val index = this.indexOf(subString)
    return if (index != -1) {
        listOf(
            this.substring(0, index),
            subString,
            this.substring(index + subString.length)
        )
    } else {
        listOf(this)
    }
}
