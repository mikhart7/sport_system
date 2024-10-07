package ru.emkn.kotlin.sms
// Номер участника - (Контрольная точка - время)
fun splitsToMap(file:String): MutableMap<Int, Map<Int, String>> {
    val csvParser =  reading(file)
    val numberToCheckPoints  = mutableMapOf<Int,Map<Int,String>>()
    for (record in csvParser){
        val checkMap = mutableMapOf<Int,String>()
        var j =1
        val size = record.size()
        if(record[1]=="241"){
        while (j<size-2 && record[j].toString()!="" && record[j].toString()!="-"){  // size-2, так как после точки время и вначале номер
            checkMap[record[j].toInt()] = record[j+1].toString()
            j+=2
        }
        }
        numberToCheckPoints[record[0].toInt()] = checkMap // сопоставление номеру (Map(Контрольная точка - время ))
    }
    return numberToCheckPoints
}

fun String.toSeconds():Int{   // перевод из "**.**.**" в секунды
    val time = this.split(":")
    return time[0].toInt()*3600+time[1].toInt()*60+time[2].toInt()
}

fun Int.toClockFormat():String{  // перевод из секунд в "**.**.**"
    val seconds = this%60
    var min = (this - seconds)%3600
    var hours = this-min-seconds
    min/=60
    hours/=3600

    var hoursS =  hours.toString()
    if(hoursS.length==1){
        hoursS = "0$hoursS"
    }
    var minS = min.toString()
    if(minS.length==1){
        minS = "0$minS"
    }
    var secondsS = seconds.toString()
    if(secondsS.length==1){
        secondsS = "0$secondsS"
    }
    return "$hoursS:$minS:$secondsS"
}

fun main() {
    println(splitsToMap("sample-data/splits.csv"))
}