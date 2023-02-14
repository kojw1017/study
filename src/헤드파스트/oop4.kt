package 헤드파스트

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.RuntimeException
import java.util.PriorityQueue

/*
576p
*/
class Station(val name: String){
    override fun equals(other: Any?): Boolean {
        if(other is Station){
            if(other.name == name) return true
        }
        return false
    }
    override fun hashCode() = name.lowercase().hashCode()
}

class Connection(val station1: Station, val station2: Station, val lineName: String)

class Subway(
    val stations: MutableList<Station>,
    val connections: MutableList<Connection>,
    val netWork: HashMap<Station, MutableList<Station>>
){
    fun addStation(stationName: String){
        if(!hasStation(stationName)) stations.add(Station(stationName))
    }
    fun hasStation(stationName: String) = stations.contains(Station(stationName))
    fun addConnection(station1Name: String, station2Name: String, lineName: String){
        if(hasStation(station1Name) && hasStation(station2Name)){
            val station1 = Station(station1Name)
            val station2 = Station(station2Name)
            val connection1 = Connection(station1, station2, lineName)
            val connection2 = Connection(station1, station2, lineName)
            connections.add(connection1)
            connections.add(connection2)
            addToNetWork(station1, station2)
            addToNetWork(station2, station1)
        } else {
           throw RuntimeException("Invalid connection")
        }
    }
    fun hasConnection(station1Name: String, station2Name: String, lineName: String): Boolean{
        val station1 = Station(station1Name)
        val station2 = Station(station2Name)
        connections.forEach {
            if(it.lineName.equals(lineName, true)){
                if(it.station1 == station1 && it.station2 == station2){
                    return true
                }
            }
        }
        return false
    }
    fun addToNetWork(station1: Station, station2: Station){
        if(netWork.keys.contains(station1)){
            val connectingStations = netWork[station1] as MutableList<Station>
            if(connectingStations.contains(station2)){
                connectingStations.add(station2)
            }
        } else {
            netWork[station1] = mutableListOf(station2)
        }
    }
}

class SubwayLoader(val subway: Subway){
    fun loadFromFile(subwayFile: File){
        val reader = BufferedReader(FileReader(subwayFile))
        loadStation(subway, reader)
        var lineName = reader.readLine()
        while (lineName != null && lineName.isNotEmpty()){
            loadLine(subway, reader, lineName)
            lineName = reader.readLine()
        }
    }
    fun loadStation(subway: Subway, reader: BufferedReader){
        var currentLine = reader.readLine()
        while (currentLine.isNotEmpty()){
            subway.addStation(currentLine)
            currentLine = reader.readLine()
        }
    }

    fun loadLine(subway: Subway, reader: BufferedReader, lineName: String){
        var station1Name = reader.readLine()
        var station2Name = reader.readLine()
        while (station2Name != null && station2Name.isNotEmpty()){
            subway.addConnection(station1Name, station2Name, lineName)
            station1Name = station2Name
            station2Name = reader.readLine()
        }
    }
}
class Solution(val operation:List<String> = listOf("I 1", "I 3", "I 5", "I 7", "D -1" )){
    fun solution(){
        val answer = mutableListOf<Int>()
        val minHead = PriorityQueue<Int>()
        val maxHead = PriorityQueue<Int>(compareByDescending { it })
        operation.forEach {
            val tmp = it.split(" ")
            when(tmp[0]){
                "I" -> {
                    minHead.add(tmp[1].toInt())
                    maxHead.add(tmp[1].toInt())
                }
                "D" -> {
                    if(minHead.isNotEmpty()){
                        if(tmp[1].toInt() > 0)
                            minHead.remove(maxHead.poll())
                        else
                            maxHead.remove(minHead.poll())
                    }
                }
            }
        }
        if(minHead.isEmpty()){
            answer += 0
            answer += 0
        }else{
            answer += maxHead.peek()
            answer += minHead.peek()
        }
        println("minHead $minHead")
        println("maxHead $maxHead")
        println(answer)
    }
}
fun main() {
    Solution().solution()
}