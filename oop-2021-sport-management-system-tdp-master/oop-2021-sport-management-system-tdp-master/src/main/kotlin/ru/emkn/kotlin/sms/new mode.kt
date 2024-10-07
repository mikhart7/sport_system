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
import ru.emkn.kotlin.sms.`windows fun`.getresults
import ru.emkn.kotlin.sms.`windows fun`.readmenu
import java.awt.Dimension
import java.awt.SystemColor.window
import java.awt.Window

//ручной ввод имеет 5 кнопок, котрые перенаправляют в окно взаимодействия с соответсвующей таблицей
@Composable
fun newmode(application: ApplicationScope,window: ComposeWindow,event: Event) {
    window.size = Dimension(300,280)
    window.title = "New Mode"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { edit(application,window,event) }
                }) {
                Text("editData")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { readmenu(application,window,event) }
                }) {
                Text("ReadData")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    if(event.spitsFile!="") {
                        event.getResultFiles()
                        window.setContent { getresults(application,window,event) }
                    }else{
                        println("No enough data")
                    }
                }) {
                Text("Get Result")
            }

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { openmenu(application,window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}