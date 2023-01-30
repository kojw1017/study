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
            println()
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
        "topWood" to Wood.MAPLE,
        "backWood" to Wood.MAPLE
    )
    val prop2 = hashMapOf<String, Any>(
        "instrumentTYPE" to InstrumentType.GUITAR,
        "builder" to Builder.FENDER,
        "model" to "D-18",
        "type" to Type.ELECTRIC,
        "topWood" to Wood.MAPLE,
        "backWood" to Wood.CEDAR
    )
    val prop3 = hashMapOf<String, Any>(
        "instrumentTYPE" to InstrumentType.GUITAR,
        "builder" to Builder.OLSON,
        "model" to "D-19",
        "type" to Type.ELECTRIC,
        "topWood" to Wood.MAPLE,
        "backWood" to Wood.COCOBOLO
    )
    val prop4 = hashMapOf<String, Any>(
        "topWood" to Wood.MAPLE
    )
    inventory.addInstrument("122784", 1433.11, InstrumentSpec(prop1))
    inventory.addInstrument("V95693", 5495.11, InstrumentSpec(prop2))
    inventory.addInstrument("V956937", 1499.11, InstrumentSpec(prop3))
    val erinLikes = InstrumentSpec(prop4)
    val instrumentList = inventory.search(erinLikes)
    printGuitar(instrumentList)
}