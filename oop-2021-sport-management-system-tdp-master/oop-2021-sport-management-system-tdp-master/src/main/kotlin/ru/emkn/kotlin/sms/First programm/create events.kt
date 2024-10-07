package ru.emkn.kotlin.sms

import java.io.File
//создается первый ивент из списка с ивентами
fun createEvents(directions: Array<String>):Event{
    correctCsvStep1(directions[0],"event")
    val event = Event(File(directions[0]).readLines().first().split(",").first(),directions[1],directions[2], directions[3])
    event.createStartProtocol()
    return event
}