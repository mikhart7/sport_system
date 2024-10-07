package ru.emkn.kotlin.sms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import java.awt.SystemColor.window
import java.awt.Window

//ручной ввод имеет 5 кнопок, котрые перенаправляют в окно взаимодействия с соответсвующей таблицей
@Composable
fun edit(application: ApplicationScope,window: ComposeWindow, event: Event) {
    /* application.apply {
         Window(
             onCloseRequest = ::exitApplication,
             title = "mnu",
             state = WindowState(width = 300.dp, height = 300.dp)
         ) {}
     }*/
    window.size = Dimension(300,400)
    window.title = "Edit Data"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent {eventWindow(application,window,event)}
                }) {
                Text("edit event")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent{classesWindow(application,window, event)}
                }) {
                Text("edit classes")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent{coursesWindow(application,window, event)}
                }) {
                Text("edit courses")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent{applicationWindow(application,window, event)}
                }) {
                Text("edit applications")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent{ splitsWindow(application,window, event) }
                }) {
                Text("edit splits")
            }

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { newmode(application,window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}