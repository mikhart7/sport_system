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
import ru.emkn.kotlin.sms.`windows fun`.getresults
import java.awt.Dimension
import java.io.File

//меню базового режима - 5 слотов для путей у файлу, работает через первоначальный main
@Composable
fun basicmode(application: ApplicationScope,window: ComposeWindow,event: Event) {
    window.size = Dimension(600, 500)
    window.title = "Basic Mode"
    var event1 = event
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            val stateevent = remember { mutableStateOf(TextFieldValue()) }

            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = stateevent.value,
                onValueChange = { stateevent.value = it },
                label = { Text("Введите путь к файлу с событием") })
            val stateclasses = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = stateclasses.value,
                onValueChange = { stateclasses.value = it },
                label = { Text("Введите путь к файлу с группами") })
            val statecourses = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = statecourses.value,
                onValueChange = { statecourses.value = it },
                label = { Text("Введите путь к файлу с дистанциями") })
            val stateapp = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = stateapp.value,
                onValueChange = { stateapp.value = it },
                label = { Text("Введите путь к папке с заявками") })
            val statesplits = remember { mutableStateOf(TextFieldValue()) }
            TextField(modifier = Modifier.align(Alignment.CenterHorizontally),
                value = statesplits.value,
                onValueChange = { statesplits.value = it },
                label = { Text("Введите путь к промежуточным результатам") })

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),

                onClick = {
                    if (File(stateevent.value.text).exists() && File(stateclasses.value.text).exists() && File(statecourses.value.text).exists() && File(stateapp.value.text).exists()){
                     event1 = createEvents(arrayOf(
                        stateevent.value.text,
                        stateclasses.value.text,
                        statecourses.value.text,
                        stateapp.value.text
                    ))
                    if (statesplits.value.text == "") {
                        event1 = getResultByFiles(
                            arrayOf(
                                stateevent.value.text,
                                stateclasses.value.text,
                                statecourses.value.text,
                                stateapp.value.text
                            )
                        )
                    } else {
                        event1 = getResultByFiles(
                            arrayOf(
                                stateevent.value.text,
                                stateclasses.value.text,
                                statecourses.value.text,
                                stateapp.value.text,
                                statesplits.value.text
                            )
                        )
                    }
                window.setContent { getresults(application,window,event1) }
                }else{
                    println("incorrect paths")
                    window.setContent { errorwin(application, window, event) }
            }}) {
                Text("Get Result")
            }

            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {

                    window.setContent { openmenu(application, window,event1) }
                }) {
                Text("Exit")
            }
        }
    }
}

@Composable
fun errorwin(application: ApplicationScope,window: ComposeWindow,event: Event){
    window.size = Dimension(300, 150)
    window.title = "Error"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            Text("Введите корректные пути к файлам")
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {

                    window.setContent { basicmode(application, window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}
