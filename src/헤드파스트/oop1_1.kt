package 헤드파스트.B

/*
 283p
 */
enum class Type(val str: String){
    ACOUSTIC("Acoustic"), ELECTRIC("Electric")
}
enum class Builder(val str: String){
    FENDER("Fender"), MARTIN("Martin"),
    GIBSON("Gibson"), COLLINGS("Collings"),
    OLSON("Olson"), RYAN("Ryan"),
    PRS("Prs"), ANY("Any"),
}
enum class Wood(val str: String){
    INDIAN_ROSEWOOD("Indian RoseWood"), BRAZILIAN_ROSEWOOD("Brazilian RoseWood"),
    MAHOGANY("Mahogany"), MAPLE("Maple"),
    COCOBOLO("Coco Bolo"), CEDAR("Cedar"),
}

enum class Style{ A, F }
enum class InstrumentType{
    GUITAR, MANDOLIN, BANJO;
    override fun toString(): String {
        return when(this) {
            GUITAR -> "Guitar"
            MANDOLIN -> "Mandolin"
            BANJO -> "Banjo"
        }
    }
}
class Instrument(val serialNumber: String, var price: Double, val spec: InstrumentSpec)
class InstrumentSpec(
    val properties: HashMap<String, Any> = hashMapOf()
){
    fun getProp(prop: String) = properties[prop]
    fun getProps() = properties

    fun matches(otherSpec: InstrumentSpec) =
        otherSpec.getProps().keys.any { properties[it] == otherSpec.getProp(it) }

}
class Inventory(private val inventory: MutableList<Instrument> = mutableListOf()) {
    fun addInstrument(serialNumber: String, price: Double, spec: InstrumentSpec){
        val instrument = Instrument(serialNumber, price, spec)
        inventory.add(instrument)
    }
    fun getGuitar(serialNumber: String): Instrument? = inventory.find { it.serialNumber == serialNumber }
    fun search(searchSpec: InstrumentSpec): List<Instrument> = inventory.filter { it.spec.matches(searchSpec) }
}

fun printGuitar(instruments: List<Instrument>){
    if(instruments.isNotEmpty()){
        instruments.forEach { instrument ->
            val spec = instrument.spec
            println("We have a ${spec.getProp("instrumentTYPE")} with the following prop")
            spec.getProps().keys.forEach {
                println("$it : ${spec.getProp(it)}")
            }
            println("You can have this ${spec.getProp("instrumentTYPE")} for ${instrument.price}")
        }
    } else println("Sorry we have nothing for you")
}

fun main() {
    val inventory = Inventory()
    val prop1 = hashMapOf<String, Any>(
        "instrumentTYPE" to InstrumentType.GUITAR,
        "builder" to Builder.GIBSON,
        "model" to "Stractocastor",
        "type" to Type.ELECTRIC,
        "backWood" to Wood.MAPLE,
        "topWood" to Wood.MAPLE
    )
    val prop2 = hashMapOf<String, Any>(
        "builder" to Builder.FENDER,
        "model" to "Stractocastor",
    )
    inventory.addInstrument("V956937", 1433.11, InstrumentSpec(prop1))
    val erinLikes = InstrumentSpec(prop2)
    val instrumentList = inventory.search(erinLikes)
    printGuitar(instrumentList)
}