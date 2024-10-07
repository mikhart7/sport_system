package ru.emkn.kotlin.sms
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
//функция исправления event, проверяет, что дата корректна
fun checkEvent(filepath: String){
    val event = File(filepath)
    val lines: List<List<String>> = csvReader().readAll(event).filter { (it.size==2) and  (it.last().split(".").size==3)}
    csvReader().readAll(event).forEach { if((!lines.contains(it)) and(it[0]!="Название")){
        val eventname:String=it[0]
        println("Неккоректное мероприятие:$eventname")} }
    csvWriter().writeAll(lines,filepath)
}
//функция, исправляющая заявки команд, когда человек имеет некорректные данные, он снимается с соревнования
fun checkApplication(filepath: String,classesMap: MutableMap<String, String>){
    val application = File(filepath)
    val lines: List<List<String>> = csvReader().readAll(application).filter {((it!= listOf("Группа","Фамилия","Имя","Г.р.","Разр.")) and (classesMap.keys.contains(it[0]))) or (csvReader().readAll(application).indexOf(it)==0)}
    csvReader().readAll(application).forEach { if((!lines.contains(it)) and(it[0]!="Группа")){
        val name:String=it[1]+" "+it[2]
        println("Нет соревнования для:$name")} }
    csvWriter().writeAll(lines,filepath)
}
//функция, исправляющая последовательности контрольных точек, все дистанции, в которых кт отмечена не числом, исключаются из списка
fun checkCourses(filepath: String){
    val courses = File(filepath)
    val lines: List<List<String>> = csvReader().readAll(courses).filter {(it.first()!="Название") and (it.subList(1,it.size-1).all { (it.toIntOrNull()!=null) or (it=="" )}) }
    csvReader().readAll(courses).forEach { if((!lines.contains(it)) and(it[0]!="Название")){
        val distance:String=it[0]
        println("Некорректная дистанция:$distance")} }
    csvWriter().writeAll(lines,filepath)
}
//функция, выкидывающая неккоректные группы
fun fixedClasses(filepath: String,courses:MutableMap<String, List<Int>>){
    val classes = File(filepath)
    val lines: List<List<String>> = csvReader().readAll(classes).filter { (it!= listOf("Название","Дистанция")) and (courses.keys.contains(it.last())) and (it.size==2)}
    csvReader().readAll(classes).forEach { if((!lines.contains(it)) and(it[0]!="Название")){
        val group:String=it[0]
        println("Некорректная группа:$group")} }
    csvWriter().writeAll(lines,filepath)
}
//функция, объединяющая функции, исправляющие таблицы
//проверка файлов, не требующих дополнительных сведений
fun correctCsvStep1(filepath: String, istype:String){
    when (istype){
        "event" -> checkEvent(filepath)
        "courses" -> checkCourses(filepath)
    }
}
//проверка файлов, в которых необходимы сведения из предыдущих файлов
fun correctCsvStep2(filepath: String,istype: String,courses:MutableMap<String, List<Int>>,classesMap: MutableMap<String, String>){
    when (istype){
        "classes" ->fixedClasses(filepath,courses)
        "application" -> checkApplication(filepath,classesMap)
    }
}