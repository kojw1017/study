package Adapter

/*
    미디에이터 패턴
    중재자
*/

interface Mediator{ fun participantChanged(participant: Participant) }
abstract class Participant(var mediator: Mediator)
class Door(mediator: Mediator): Participant(mediator){
    private var bClosed = true
    fun open(){
        if(!bClosed) return
        bClosed = false
        mediator.participantChanged(this)
    }
    fun close(){
        if(!bClosed) return
        bClosed = true
        mediator.participantChanged(this)
    }
    fun isClosed() = bClosed
    override fun toString(): String {
        return if(bClosed) "# 문 : 닫힘"
        else "# 문 : 열림"
    }
}
class Window(mediator: Mediator): Participant(mediator){
    private var bClosed = true
    fun open(){
        if(!bClosed) return
        bClosed = false
        mediator.participantChanged(this)
    }
    fun close(){
        if(!bClosed) return
        bClosed = true
        mediator.participantChanged(this)
    }
    fun isClosed() = bClosed
    override fun toString(): String {
        return if(bClosed) "# 창 : 닫힘"
        else "# 창 : 열림"
    }
}
class HeatBoiler(mediator: Mediator): Participant(mediator){
    private var bClosed = true
    fun on(){
        if(!bClosed) return
        bClosed = false
        mediator.participantChanged(this)
    }
    fun off(){
        if(!bClosed) return
        bClosed = true
        mediator.participantChanged(this)
    }
    fun isClosed() = bClosed
    override fun toString(): String {
        return if(bClosed) "# 보일러 : 꺼짐"
        else "# 보일러 : 켜짐"
    }
}
class CoolAircon(mediator: Mediator): Participant(mediator){
    private var bClosed = true
    fun on(){
        if(!bClosed) return
        bClosed = false
        mediator.participantChanged(this)
    }
    fun off(){
        if(!bClosed) return
        bClosed = true
        mediator.participantChanged(this)
    }
    fun isClosed() = bClosed
    override fun toString(): String {
        return if(bClosed) "# 에어컨 : 꺼짐"
        else "# 에어컨 : 켜짐"
    }
}
class SmartHome: Mediator{
    var door = Door(this)
    var window = Window(this)
    var aircon = CoolAircon(this)
    var boiler = HeatBoiler(this)
    override fun participantChanged(participant: Participant) {
        if(participant == door && !door.isClosed()){
            aircon.off()
            boiler.off()
        }
        if(participant == window && !window.isClosed()){
            aircon.off()
            boiler.off()
        }
        if(participant == aircon && !aircon.isClosed()){
            boiler.off()
            window.close()
            door.close()
        }
        if(participant == boiler && !boiler.isClosed()){
            aircon.off()
            window.close()
            door.close()
        }
    }

    fun report(){
        println("""
            =================================
            집안 꼬라지
            $door
            $window
            $aircon
            $boiler
            =================================
        """.trimIndent())
    }
}
fun main() {
    val home = SmartHome()
    do {
        home.report()
        print("명령 :")
        when(readLine()!!.toInt()){
            1 -> home.door.open()
            2 -> home.door.close()
            3 -> home.window.open()
            4 -> home.window.close()
            5 -> home.aircon.on()
            6 -> home.aircon.off()
            7 -> home.boiler.on()
            8 -> home.boiler.off()
        }
    }while (true)
}
