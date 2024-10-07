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
fun coursesWindow(application: ApplicationScope,window:ComposeWindow,event: Event){
    window.size = Dimension(300,300)
    MaterialTheme {
        Column (Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            val distance = remember { mutableStateOf(TextFieldValue()) }

            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = distance.value,
                onValueChange = { distance.value = it },
                label = { Text("Distance") })

            val checkPoints = remember { mutableStateOf(TextFieldValue()) }
            val checkMap = checkPoints.value.text.split(",")
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = checkPoints.value,
                onValueChange = { checkPoints.value = it },
                label = { Text("Checkpoints") })

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    val l = mutableListOf<Int>()
                    checkMap.forEach{ l.add(it.toInt())}

                    event.coursesMap.remove(distance.value.text)
                    event.coursesMap[distance.value.text] = l
                    event.allGroups.groups.forEach {
                        if(it.distance.distanceName==distance.value.text){
                            it.distance.checkPoints = l
                        }
                    }
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