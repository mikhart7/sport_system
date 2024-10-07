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
fun classesWindow(application: ApplicationScope,window:ComposeWindow,event: Event){
    window.size = Dimension(300,300)
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            val groupName = remember { mutableStateOf(TextFieldValue()) }

            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = groupName.value,
                onValueChange = { groupName.value = it },
                label = { Text("Group") })

            val distance = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = distance.value,
                onValueChange = { distance.value = it },
                label = { Text("Distance") })

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {

                    event.classesMap.remove(groupName.value.text)
                    event.classesMap[groupName.value.text] = distance.value.text
                    event.allGroups.groups.forEach {
                        if(it.groupName == groupName.value.text){
                            if(event.coursesMap[distance.value.text]!=null) {
                                it.distance = Distance(distance.value.text,requireNotNull(event.coursesMap[distance.value.text]){ println("No checkpoints")} )
                            }
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