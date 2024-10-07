
import ru.emkn.kotlin.sms.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.*

internal class TaskTest {

    @Test
    fun test1() {
        val group = Group("Spb", Distance("43M", listOf(2,3,4,5,6)))
        val participant1 = Participant("Spb","Lask","Hut","2001",null,"")
        val participant2 = Participant("Spb","Task","Mut","1987","21","")
        val participant3 = Participant("Spb","Pask","Kut","1999","19","")
        val participant4 = Participant("Spb","Gask","Lut","1978",null,"")
        val participant5 = Participant("Spb","Fask","Put","1988",null,"")
        participant1.result = 6700
        participant2.result = 5600
        participant3.result = 8300
        participant4.result = 5200
        participant5.result = 5100
        group.groupMembers = listOf(participant1,participant2,participant3,participant4,participant5).toMutableList()
        group.sortMembers()
        assertEquals(listOf(2,3,4,5,6),group.distance.checkPoints)
        assertEquals(5100,group.winnerResult)
        assertEquals(3,participant3.number)
        assertEquals(90.19607843137254,participant2.points)
        assertEquals("+00:08:20",participant2.loseTime)
        assertEquals("+00:01:40",participant4.loseTime)
    }
    @Test
    fun test2(){
        val a = 8920
        val s = "12:18:56"
        assertEquals("02:28:40",a.toClockFormat())
        assertEquals(44336,s.toSeconds())
    }
    @Test
    fun testevent(){
        File("test-data/eventtest.csv").writeText(File("sample-data/event.csv").readText())
        checkEvent("test-data/eventtest.csv")
        assertEquals("Первенство пятой бани,01.01.2022\n".trim(),File("test-data/eventtest.csv").readText().trim())
    }
    @Test
    fun testcourses(){
        File("test-data/coursestest.csv").writeText(File("sample-data/courses.csv").readText())
        checkCourses("test-data/coursestest.csv")
        assertEquals(
            listOf("М18 21 40 50,31,32,33,34,35,36,37,38,39,40,41,39,42,43,44,45,46,47,48,49,50,51,49,52,53" ,
                "МЖ9 10,32,46,34,33,53,,,,,,,,,,,,,,,,,,,," ,
                "Ж14,47,46,45,34,33,32,48,52,51,50,49,53,,,,,,,,,,,,," ,
                "Ж12,32,46,33,47,48,52,51,50,49,53,,,,,,,,,,,,,,," ,
                "М12,32,46,34,35,45,33,47,48,52,49,53,,,,,,,,,,,,,," ,
                "М14,31,32,33,34,35,36,37,44,45,46,47,48,53,,,,,,,,,,,," ,
                "Ж21 40 М60,33,34,35,36,43,42,39,40,38,37,44,45,46,32,48,49,50,51,49,52,53,0,,," ,
                "М16 Ж60,31,32,33,34,35,36,43,37,44,45,46,47,48,49,51,52,53,,,,,,,," ,
                "Ж16 студенты,32,33,34,44,36,37,35,45,46,47,48,49,52,53,,,,,,,,,,,"),File("test-data/coursestest.csv").readLines())
    }
    @Test
    fun testclasses(){
        File("test-data/eventtest.csv").writeText(File("sample-data/event.csv").readText())
        File("test-data/coursestest.csv").writeText(File("sample-data/courses.csv").readText())
        File("test-data/classestest.csv").writeText(File("sample-data/classes.csv").readText())
        val coursesMap: MutableMap<String, List<Int>> = mutableMapOf()
        correctCsvStep1("test-data/coursestest.csv", "courses")
        openCSV("test-data/coursestest.csv").forEach {
            val checkpoints: MutableList<Int> = mutableListOf(241) // старт
            it.subList(1, it.size - 1).forEach {
                if (it != "") {
                    checkpoints.add(it.toInt())
                }
            }
            checkpoints.add(240) // финиш
            coursesMap[it.first()] = checkpoints
        }
        correctCsvStep2("test-data/classestest.csv", "classes", coursesMap, mutableMapOf())
        assertEquals(
            listOf("М10,МЖ9 10" ,
                "М12,М12" ,
                "М14,М14" ,
                "М16,М16 Ж60" ,
                "М18,М18 21 40 50" ,
                "М21,М18 21 40 50" ,
                "М40,М18 21 40 50" ,
                "М60,Ж21 40 М60" ,
                "Ж10,МЖ9 10" ,
                "Ж12,Ж12" ,
                "Ж14,Ж14" ,
                "Ж16,Ж16 студенты" ,
                "Ж18,Ж21 40 М60" ,
                "Ж21,Ж21 40 М60" ,
                "Ж40,Ж21 40 М60" ,
                "Ж60,М16 Ж60" ,
                "Мстуд,Ж16 студенты" ,
                "Жстуд,Ж16 студенты" ,
                "VIP,МЖ9 10"),File("test-data/classestest.csv").readLines())
    }
    @Test
    fun testapp(){
        File("test-data/eventtest.csv").writeText(File("sample-data/event.csv").readText())
        File("test-data/coursestest.csv").writeText(File("sample-data/courses.csv").readText())
        File("test-data/classestest.csv").writeText(File("sample-data/classes.csv").readText())


        val coursesMap: MutableMap<String, List<Int>> = mutableMapOf()
        val classesMap: MutableMap<String, String> = mutableMapOf()
        correctCsvStep1("test-data/coursestest.csv", "courses")
        openCSV("test-data/coursestest.csv").forEach {
            val checkpoints: MutableList<Int> = mutableListOf(241) // старт
            it.subList(1, it.size - 1).forEach {
                if (it != "") {
                    checkpoints.add(it.toInt())
                }
            }
            checkpoints.add(240) // финиш
            coursesMap[it.first()] = checkpoints
        }
        correctCsvStep2("test-data/classestest.csv", "classes", coursesMap, mutableMapOf())
        openCSV("test-data/classestest.csv").forEach {
            classesMap[it.first()] = it.last()
        }
        checkApplication("test-data/applications/applicationtest1.csv",classesMap)
        checkApplication("test-data/applications/applicationtest7.csv",classesMap)
        checkApplication("test-data/applications/applicationtest10.csv",classesMap)
        assertEquals(File("test-data/applications/applicationtest1.csv").readLines(),File("test-data/checkapp1.csv").readLines())
        assertEquals(File("test-data/applications/applicationtest7.csv").readLines(),File("test-data/checkapp7.csv").readLines())
        assertEquals(File("test-data/applications/applicationtest10.csv").readLines(),File("test-data/checkapp10.csv").readLines())
    }
}
