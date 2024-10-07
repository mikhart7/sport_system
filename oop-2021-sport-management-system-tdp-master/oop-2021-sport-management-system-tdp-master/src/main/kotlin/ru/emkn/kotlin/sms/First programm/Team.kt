package ru.emkn.kotlin.sms
//класс команды
class Team(val teamName: String) {
    var teamMembers =  mutableListOf<Participant>()

    fun add(participant: Participant){
        teamMembers.add(participant)
    }
    fun teamResult():Double{
        return teamMembers.sumOf { it.points }
    }
}