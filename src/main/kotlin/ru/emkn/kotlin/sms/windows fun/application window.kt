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
fun applicationWindow(application: ApplicationScope,window:ComposeWindow,event: Event){
    window.size = Dimension(350,550)
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            val team = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = team.value,
                onValueChange = { team.value = it },
                label = { Text("Team") })

            val group = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = group.value,
                onValueChange = { group.value = it },
                label = { Text("Group") })

            val lastname= remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = lastname.value,
                onValueChange = { lastname.value = it },
                label = { Text("lastname") })

            val firstname = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = firstname.value,
                onValueChange = { firstname.value= it },
                label = { Text("firstname") })

            val birthYear = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = birthYear.value,
                onValueChange = { birthYear.value = it },
                label = { Text("birthYear") })

            val rank = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = rank.value,
                onValueChange = { rank.value = it },
                label = { Text("Rank") })


            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    val participant = Participant(group.value.text,lastname.value.text,firstname.value.text,birthYear.value.text,rank.value.text,team.value.text)
                    if(event.allGroups.groups.find { it.groupName == group.value.text }!=null) {
                        requireNotNull(event.allGroups.groups.find { it.groupName == group.value.text }) { println("Group isn't exist") }.groupMembers.add(participant)
                    }
                }) {
                Text("add Participant")
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