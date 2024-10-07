package ru.emkn.kotlin.sms

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application


/** вводим стандарт входных данных
первый аргумент - путь к файлу с event
второй аргумент - путь к classes
третий - путь к папке с courses
чевертый - путь к папке с applications, в которой храняться файлы заявок
пятый, не обязателен - путь к splits, при его вводе выдаются результаты
 **/
fun getResultByFiles(args: Array<String>):Event {
    val event =  createEvents(args)
    if(args.size==5) {
        event.spitsFile = args[4]
        event.getResultFiles()
    }
    return event
}

fun main(args: Array<String>) {
        application {
            val applicationScope = this
            Window(
                onCloseRequest = ::exitApplication,
                title = "main menu",
                state = WindowState(width = 400.dp, height = 400.dp)

            ) {
                val args1:Array<String>
                if (args.size==0){
                    args1 = arrayOf("zeroarguments\\eventtest.csv","zeroarguments\\classestest.csv","zeroarguments\\coursestest.csv","zeroarguments\\zeroapp")
                }else{
                    args1 = args
                }
                val event = createEvents(args1)
                if (args1.size == 5) {
                    event.spitsFile = args1[4]
                }
                openmenu(applicationScope,window, event)
            }
    }
}

// Протоколы получены по  этим файлам.
// filesForGettingResult\work-data\event.csv
// filesForGettingResult\work-data\classes.csv
// filesForGettingResult\work-data\courses.csv
// filesForGettingResult\work-data\applications
// filesForGettingResult\work-data\splits.csv