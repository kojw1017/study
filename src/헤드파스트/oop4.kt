package 헤드파스트

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.RuntimeException

/*
560p
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

class Subway(val stations: MutableList<Station>, val connections: MutableList<Connection>){
    fun addStation(stationName: String){
        if(!hasStation(stationName)){
            stations.add(Station(stationName))
        }
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
        }else {
           throw RuntimeException("Invalid connection")
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
fun main() {

}