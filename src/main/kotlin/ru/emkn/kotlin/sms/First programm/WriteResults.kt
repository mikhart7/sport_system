package ru.emkn.kotlin.sms
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedReader
import java.io.BufferedWriter
import java.nio.file.Files
import java.nio.file.Paths


// считываем
fun reading(filename: String): CSVParser {
    val reader: BufferedReader = Files.newBufferedReader(Paths.get(filename))
    return CSVParser(reader, CSVFormat.DEFAULT)
}




fun writeTeamsResult(event: Event) {
    val writer: BufferedWriter = Files.newBufferedWriter(Paths.get("Event${event.name}_Teams.csv"))
    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT
        .withHeader("Место", "Название команды", "Результат"))
    val teams = event.teams
    var i =1
    for(team in teams) {
        csvPrinter.printRecord(i,team.teamName,team.teamResult())
        i++
    }
    csvPrinter.flush()
}

fun writeResults(event: Event) {
    val writer: BufferedWriter = Files.newBufferedWriter(Paths.get("${event.name}_AllResults.csv"))
    val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)
    csvPrinter.printRecord("Протокол результатов",null,null,null,null,null,null,null,null,null)
    val allGroups = event.allGroups
     allGroups.groups.forEach { it ->
        csvPrinter.printRecord("${it.groupName},,,,,,,,,")
        csvPrinter.printRecord("№ п/п","Номер","Фамилия","Имя","Г.р.","Разр.","Команда","Результат","Место","Отставание")
        var j =1
        it.groupMembers.forEach {
            if(it.result<0){
              csvPrinter.printRecord(j,it.number,it.lastname,it.firstname,it.birthYear,it.rank,it.team,"выбыл",null,null)
            }
            else{
                csvPrinter.printRecord(j,it.number,it.lastname,it.firstname,it.birthYear,it.rank,it.team,it.result.toClockFormat(),j,it.loseTime)
            }
            j++
        }

    }



    csvPrinter.flush()

}

