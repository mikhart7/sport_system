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
import androidx.compose.ui.window.*
import java.awt.Dimension

//меню базового режима - 5 слотов для путей у файлу, работает через первоначальный main
@Composable
fun splitsWindow(application: ApplicationScope,window: ComposeWindow,event: Event) {
    window.size = Dimension(300,180)
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            val splits = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = splits.value,
                onValueChange = { splits.value = it },
                label = { Text("Введите путь к файлу с splits") })
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    event.spitsFile = splits.value.text
                    window.setContent { edit(application, window, event) }
                }) {
                Text("exit")
            }
        }
    }
}