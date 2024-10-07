package ru.emkn.kotlin.sms
//класс со всеми группами
class AllGroups(classesMap:MutableMap<String,String>,coursesMap: Map<String,List<Int>>) {

    val groups = mutableListOf<Group>()
    init{
        classesMap.forEach{ groups.add( Group(it.key,Distance(it.value, coursesMap[it.value]!!  )))  }
    }
    fun addParticipant(participant: Participant){
        for (i in 0 until (groups.size)){
            if (groups[i].groupName == participant.groupName){
                groups[i].addParticipantInGroup(participant)
            }
        }
    }

}
