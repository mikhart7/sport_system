package ru.emkn.kotlin.sms



data class Distance(val distanceName: String, var checkPoints:List<Int>)
//класс группы, хранящий всех участников, дистанцию для этой группы.Класс имеет функцию распределения результатов внутри группы
class Group(var groupName: String, var distance: Distance) {
    var groupMembers =  mutableListOf<Participant>()
    private var order = mutableListOf<Participant>()
    var winnerResult  = 0
    fun getGroupResult(numberToCheckPoints: MutableMap<Int, Map<Int, String>>){
        groupMembers.forEach {
          it.getPersonalResult(numberToCheckPoints,this)
        }
    }
    companion object {
        fun sort(participant1: Participant, participant2: Participant): Int {
            if(participant1.result==-1){
                return 1
            }
            else{
                if(participant2.result==-1){
                    return -1
                }
                else {
                    return participant1.result - participant2.result
                }
            }
        }
    }
    fun sortMembers(){
        groupMembers = groupMembers.sortedWith { x, y -> sort(x,y)  }.toMutableList()
        winnerResult = groupMembers.first().result
        groupMembers.forEach{
            it.getPoints(this)
            it.getLoseTime(this)
        }
    }
    fun addParticipantInGroup(participant: Participant){
        groupMembers.add(participant)
    }
    fun randomOrder(pravilnyporydok: MutableList<Participant> = mutableListOf()): MutableList<Participant> {
        if (pravilnyporydok.isNotEmpty()){
            order = pravilnyporydok
            groupMembers = pravilnyporydok
        }else if(order.isEmpty()){
            order = groupMembers.shuffled(java.util.Random(1)).toMutableList()
            var startTime = 12*3600
            for (i in 0 until order.size){
                for (j in 0 until groupMembers.size){
                    if (order[i] == groupMembers[j]){
                        groupMembers[j].startTime = startTime.toClockFormat()
                    }
                }
                order[i].startTime = startTime.toClockFormat()
                startTime += 60
            }
        }
        return order
    }

}


