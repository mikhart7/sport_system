package ru.emkn.kotlin.sms

import java.io.File


//класс event, в котором собрана вся информация о мероприятии: список команд и список групп, в этом же классе присваиваются номера и время для старта
//через специальные функции
class Event(var name: String, classes: String, courses: String, filepathToApplication: String ) {
    var coursesMap: MutableMap<String, List<Int>> = mutableMapOf() // Дистанция - контрольные точки
    var classesMap: MutableMap<String, String> = mutableMapOf()  // Группа - Дистанция

    //происходит запись всех дистанций и групп
    init {
        correctCsvStep1(courses, "courses")
        openCSV(courses).forEach {
            val checkpoints: MutableList<Int> = mutableListOf(241) // старт
            it.subList(1, it.size - 1).forEach {
                if (it != "") {
                    checkpoints.add(it.toInt())
                }
            }
            checkpoints.add(240) // финиш
            coursesMap[it.first()] = checkpoints
        }
        correctCsvStep2(classes, "classes", coursesMap, mutableMapOf())
        openCSV(classes).forEach {
            classesMap[it.first()] = it.last()
        }
    }
    var spitsFile = ""
    var teams = mutableListOf<Team>()

    var allGroups = AllGroups(this.classesMap, this.coursesMap)
//обработка заявочных списков
    init {
       getApplications(filepathToApplication)
    }


    fun createStartProtocol(filepath : String = "") {

        for (i in allGroups.groups){
            val order = i.randomOrder()
         //   println(order)
            val aplicaton = File(filepath+i.groupName+".csv")
            var text =""
            for (line in order){
                text += line.number.toString() + "," + line.lastname + "," + line.firstname + "," + line.startTime + "\n"
            }
            aplicaton.writeText(text)
        }
    }

     fun getSplits(): MutableMap<Int, Map<Int, String>> {
        return splitsToMap(spitsFile)      // Номер участника - (Контрольная точка - время)
    }


    fun getAllGroupResult() {
        val numberToCheckPoints = getSplits()
        allGroups.groups.forEach {
            it.getGroupResult(numberToCheckPoints)
            it.sortMembers()
        }
    }

    fun getSortedTeamsByResult() {
        teams.sortBy {-it.teamResult() }
    }

    fun getResultFiles() {
        getAllGroupResult()
        writeResults(this)
        getSortedTeamsByResult()
        writeTeamsResult(this)
    }


    private fun getApplications(filepathtoapplication: String) {
        val filepathToApplication = allCSVFilesInDirections(filepathtoapplication)
        for (i in filepathToApplication) {
            correctCsvStep2(i, "application",coursesMap,classesMap)
            val application = openCSV(i)
            val nameteam = application[0][0]
            val team = Team(nameteam)
            for (practicals in application) {
                val participant = Participant(practicals[0], practicals[1], practicals[2], practicals[3], practicals[4],nameteam )
                if(practicals[2]!=""){
                  team.add(participant)
                }

                allGroups.addParticipant(participant)

            }
            teams.add(team)
        }


    }
}