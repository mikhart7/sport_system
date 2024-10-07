package ru.emkn.kotlin.sms.`windows fun`

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import ru.emkn.kotlin.sms.Event
import ru.emkn.kotlin.sms.basicmode
import ru.emkn.kotlin.sms.newmode
import java.awt.Dimension

//на вход подается рабочее приложение(application) ивент, что должно считываться, имя если считывается сплит или команда
//пока не знаю, где вытащить splits
@Composable
fun Readfun(application: ApplicationScope, window: ComposeWindow, event: Event, readObject:String, name:String="") {
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            var text = ""
            when (readObject) {
                "event" -> {text = event.name;window.size=Dimension(230,130)}
                "classes" -> {window.size= Dimension(230,170);
                    event.classesMap.forEach { t, u -> text += "$t - $u\n" }
                }
                "courses" -> {
                    event.coursesMap.forEach {
                        text += it.key + ":"
                        it.value.forEach { text += "${it} " }
                        text += "\n"
                    }
                }
                "application" -> {
                    window.size = Dimension(400,150);
                    val team = event.teams.find { it.teamName == name }
                    if (team==null){
                        text="неккоректная команда"
                    }else {
                        team.teamMembers.forEach {
                            text += "${it.firstname} ${it.lastname} - ${it.number} in ${it.groupName}" + "\n"
                        }
                    }
                }
                "splits" -> {
                    window.size = Dimension(300, 600);
                    val id = name.toInt()
                    if (name == "") {
                        text ="Введите номер участника"
                    } else {
                        if (event.spitsFile != "") {
                            if ((event.getSplits()[id] == null)) {
                                text = "Такого спортсмена нет"
                            } else {
                                event.getSplits()[id]!!.forEach {
                                    text += "${it.key} - ${it.value}\n"
                                }
                            }
                        } else {
                            text = "Введите протокол прохождения контрольных пунктов"
                        }
                    }
                }
            }
            Text(text)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { readmenu(application, window, event) }
                }) {
                Text("Exit")
            }
        }

    }
}
@Composable
fun readmenu(application: ApplicationScope, window: ComposeWindow, event: Event){
    window.size = Dimension(500,500)
    window.title = "Read data"
    MaterialTheme{
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { Readfun(application,window,event,"event") }
                }) {
                Text("Event")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { Readfun(application,window,event,"classes") }
                }) {
                Text("Classes")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { Readfun(application,window,event,"courses") }
                }) {
                Text("Courses")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { applicationread(application, window, event) }
                }) {
                Text("Application")
            }

            val id = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = id.value,
                onValueChange = { id.value = it },
                label = { Text("Введите номер участника") })
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    if (id.value.text!="") {
                        window.setContent { Readfun(application, window, event, "splits", id.value.text) }
                    }else{
                        window.setContent { errorsplit(application, window, event) }
                    }
                }) {
                Text("Splits")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent {newmode(application,window,event)}
                }) {
                Text("Exit")
            }
        }
    }
}
@Composable
fun applicationread(application: ApplicationScope, window: ComposeWindow, event: Event){
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { listteams(application,window,event) }
                }) {
                Text("Список команд")
            }
            val nameapp = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = nameapp.value,
                onValueChange = { nameapp.value = it },
                label = { Text("Введите название команды") })

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { Readfun(application,window,event,"application",nameapp.value.text) }
                }) {
                Text("Application")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { readmenu(application,window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}
@Composable
fun listteams(application: ApplicationScope, window: ComposeWindow, event: Event){
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            var text=""
            event.teams.forEach {
                text+=it.teamName+"\n"
            }
            Text(text)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { readmenu(application, window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}
@Composable
fun errorsplit(application: ApplicationScope, window: ComposeWindow, event: Event){
    window.size = Dimension(300, 150)
    window.title = "Error"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            Text("Введите корректный номер участника")
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { readmenu(application, window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}