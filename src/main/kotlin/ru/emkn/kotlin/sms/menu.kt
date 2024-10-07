package ru.emkn.kotlin.sms

import androidx.compose.foundation.layout.*

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import java.awt.Dimension

@Composable
//меню с 2 кнопками режима

fun openmenu(application: ApplicationScope,window: ComposeWindow,event: Event){
    window.size= Dimension(300,160)
     MaterialTheme {
                Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {

                                window.setContent { newmode(application,window,event)}


                        }) {
                        Text("new mode")
                    }
                    Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            window.setContent { basicmode(application,window,event)  }
                        }) {
                        Text("Basic mode") }

                }
            }
        }



