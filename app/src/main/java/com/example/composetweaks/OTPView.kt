package com.example.composetweaks

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OTPView(
    numberOfFields: Int = 6
) {
    var text by remember {
        mutableStateOf("")
    }

    val focuses = remember {
        List(numberOfFields) {
            FocusRequester()
        }
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(numberOfFields) { index ->
                TextField(
                    value = text.tryGet(index),
                    onValueChange = {
                        if (it.length == 1) {
                            //  it means user is typing the otp, so move the focus to next tf as the otp is entered
                            focuses[index].freeFocus()
                            if (index < numberOfFields - 1)
                                focuses[index + 1].requestFocus()
                            else
                                focusManager.clearFocus(force = true)

                            text += it
                        } else if (it.length == numberOfFields) {
                            //means user is pasting the otp, so use the text and clear focus.
                            text = it
                            focusManager.clearFocus(true)
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focuses[index])
                        .onKeyEvent { event ->
                            if (event.key == Key.Backspace) {
                                //move focus to prev tf and drop the last digit from otp
                                if (index > 0)
                                    focuses[index - 1].requestFocus()
                                else
                                    focusManager.clearFocus(true)
                                text = text.dropLast(1)
                            }
                            true
                        }
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(shape = RoundedCornerShape(12.dp), color = Color.White)
                        .border(
                            shape = RoundedCornerShape(12.dp),
                            width = 1.dp,
                            color = Color.Gray
                        ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.None
                    ),
                    singleLine = true,
                    textStyle = TextStyle(textAlign = TextAlign.Start),
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        if (index > 0 && text.isEmpty()) //force the focus to go to first tf even if user click on any other field if the text is empty yet
                                            focuses[0].requestFocus()
                                        else if (text.length < numberOfFields - 1) //focus the tf next to the previously filled field even if user clicks any other field.
                                            focuses[text.length].requestFocus()
                                    }
                                }
                            }
                        }
                )
            }
        }


        Button(
            onClick = {
                Log.e("otp", text)
            },
            enabled = text.length == numberOfFields
        ) {
            Text(text = "Print OTP")
        }
    }
}


fun String.tryGet(index: Int): String {
    return try {
        this[index].toString()
    } catch (e: Exception) {
        ""
    }
}