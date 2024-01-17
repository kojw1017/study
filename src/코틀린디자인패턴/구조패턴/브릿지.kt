package 코틀린디자인패턴.구조패턴
// Implementor
interface Device {
    fun turnOn()
    fun turnOff()
    fun setChannel(channel: Int)
}

// Concrete Implementors
class TV : Device {
    override fun turnOn() = println("TV turned on")
    override fun turnOff() = println("TV turned off")
    override fun setChannel(channel: Int) = println("TV set to channel $channel")
}

class Radio : Device {
    override fun turnOn() = println("Radio turned on")
    override fun turnOff() = println("Radio turned off")
    override fun setChannel(channel: Int) = println("Radio set to frequency $channel")
}

// Abstraction
abstract class RemoteControl(protected val device: Device) {
    abstract fun togglePower()
    abstract fun switchChannel(channel: Int)
}

// Refined Abstraction
class BasicRemoteControl(device: Device) : RemoteControl(device) {
    override fun togglePower() {
        println("Toggle power using Basic Remote:")
        device.turnOn()
    }

    override fun switchChannel(channel: Int) {
        device.setChannel(channel)
    }
}

// Client Code
fun main() {
    val tv = TV()
    val radio = Radio()

    val tvRemote = BasicRemoteControl(tv)
    tvRemote.togglePower()
    tvRemote.switchChannel(5)

    val radioRemote = BasicRemoteControl(radio)
    radioRemote.togglePower()
    radioRemote.switchChannel(101)
}
