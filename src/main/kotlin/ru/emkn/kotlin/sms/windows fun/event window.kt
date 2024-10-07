package ru.emkn.kotlin.sms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import java.awt.Dimension


@Composable
fun eventWindow(application: ApplicationScope,window:ComposeWindow,event: Event){
    window.size = Dimension(300,200)
    MaterialTheme {
        Column (Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            val eventName = remember { mutableStateOf(TextFieldValue()) }

            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = eventName.value,
                onValueChange = { eventName.value = it },
                label = { Text("Изменить имя") })

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    event.name = eventName.value.text
                }) {
                Text("edit")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { edit(application, window, event) }
                }) {
                Text("exit")
            }
        }
    }

}