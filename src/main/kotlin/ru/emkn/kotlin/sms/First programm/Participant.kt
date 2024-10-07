package ru.emkn.kotlin.sms

import kotlin.math.max

//класс спортсменов, хранит всю информацию об участнике
data class Participant(val groupName:String, val lastname: String, val firstname: String, val birthYear:String, var rank:String?,val team:String) {
    init{
        numberParticipant++
    }
    val number = numberParticipant

   var startTime ="00.00.00"
    var result = -1
    var loseTime = ""
    fun getPersonalResult(numberToCheckPoints: MutableMap<Int, Map<Int, String>>,group: Group){
        if (numberToCheckPoints.keys.contains(this.number)) {
            val checkMap = requireNotNull(numberToCheckPoints[this.number])

            // Проверка мн-ва точек спортсмена и точек группы ( правильная подпоследовательность )
            if (checkMap.keys.toList().filter { group.distance.checkPoints.contains(it)}.distinct() !=group.distance.checkPoints) {
               this.result = -1    // если выбыл
            } else {
                this.result = requireNotNull(checkMap[240]).toSeconds() - this.startTime.toSeconds()
            }
        }
    }
    var points = 0.0
    fun getPoints(group: Group) {
        if(this.result==-1){
            this.points=0.0
        }
        else {
            this.points = max(0.0, 100 * (2 - this.result.toDouble() / group.winnerResult))
        }
    }
    fun getLoseTime(group: Group){
        this.loseTime = '+'+(this.result-group.winnerResult).toClockFormat()
    }
    companion object{
        var numberParticipant = 0
    }
}