package ru.emkn.kotlin.sms.`windows fun`

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
import ru.emkn.kotlin.sms.Event
import ru.emkn.kotlin.sms.newmode
import ru.emkn.kotlin.sms.openCSV
import ru.emkn.kotlin.sms.openmenu
import java.awt.Dimension

@Composable
fun getresults(application: ApplicationScope, window: ComposeWindow, event: Event){
    window.size = Dimension(300,200)
    window.title = "Get result"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { personalresults(application,window,event,) }
                }) {
                Text("Personal results")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    window.setContent { teamresults(application,window,event,) }
                }) {
                Text("Team results")
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
@Composable
fun personalresults(application: ApplicationScope, window: ComposeWindow, event: Event){
    window.size = Dimension(500,700)
    window.title = "New Mode"
    MaterialTheme {
        Column (Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)){
            var text = ""
            text+="результаты в группах\n"
            openCSV("${event.name}_AllResults.csv").forEach {
                if ((it.first()!="\"Протокол результатов\"") && (it.first()!="\"№ п/п\"") && (it.first()[0]=='"')){
                    text+=it.first().subSequence(1,it.first().length)
                    text+="\n"
                }else{
                    if (!it.first().startsWith('"')){
                        text+="${it.first()} место - участник под номером ${it[1]} ${it[2]} ${it[3]} с результатом ${it[7]}\n"
                    }
                }
            }
            Text(text)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { getresults(application,window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}
@Composable
fun teamresults(application: ApplicationScope, window: ComposeWindow, event: Event){
    window.size = Dimension(500,700)
    window.title = "New Mode"
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(8.dp)) {
            var text = ""
            openCSV("Event${event.name}_Teams.csv").forEach {
                if (it.first()!="\"Место\""){
                    text+="${it[0]} место заняла команда ${it[1]} с результатом ${it[2]}\n"
                }
            }
            Text(text)
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    window.setContent { getresults(application,window,event) }
                }) {
                Text("Exit")
            }
        }
    }
}