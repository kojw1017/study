package 헤드파스트
/*
 244p
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

abstract class Instrument(val serialNumber: String, var price: Double, val instrument: InstrumentSpec)
class Guitar(serialNumber: String, price: Double, spec: GuitarSpec): Instrument(serialNumber, price, spec)
class Mandolin(serialNumber: String, price: Double, spec: MandolinSpec): Instrument(serialNumber, price, spec)
open class InstrumentSpec(
    var builder: Builder,
    var model: String,
    var type: Type,
    var backWood: Wood,
    var topWood: Wood
){
    open fun matches(otherSpec: InstrumentSpec): Boolean {
        if(builder != otherSpec.builder) return false
        if(model.isNotEmpty() && model.lowercase() != otherSpec.model.lowercase()) return false
        if(type != otherSpec.type) return false
        if(backWood != otherSpec.backWood) return false
        if(topWood != otherSpec.topWood) return false
        return true
    }
}

class GuitarSpec(
    builder: Builder,
    model: String,
    type: Type,
    backWood: Wood,
    topWood: Wood,
    val numStrings: Int
): InstrumentSpec(builder, model, type, backWood, topWood){
    override fun matches(otherSpec: InstrumentSpec): Boolean {
        if(!super.matches(otherSpec)) return false
        if(otherSpec is GuitarSpec) {
            if(otherSpec.numStrings != numStrings) return false
        }else return false
        return true
    }
}

class MandolinSpec(
    builder: Builder,
    model: String,
    type: Type,
    backWood: Wood,
    topWood: Wood,
    val style: Style
): InstrumentSpec(builder, model, type, backWood, topWood){
    override fun matches(otherSpec: InstrumentSpec): Boolean {
        if(!super.matches(otherSpec)) return false
        if(otherSpec is MandolinSpec) {
            if(style != otherSpec.style) return false
        }else return false
        return true
    }
}

class Style

class Inventory(val inventory: MutableList<Instrument> = mutableListOf()) {
    fun addInstrument(serialNumber: String, price: Double, spec: InstrumentSpec){
        var instrument:Instrument? = null
        if(spec is GuitarSpec) instrument = Guitar(serialNumber, price, spec)
        else if(spec is MandolinSpec) instrument = Mandolin(serialNumber, price, spec)
        instrument?.let { inventory.add(it) }
    }
    fun getGuitar(serialNumber: String): Instrument? = inventory.find { it.serialNumber == serialNumber }
    fun search(searchSpec: MandolinSpec): List<Instrument> = inventory.filter { it.instrument.matches(searchSpec) }
    fun search(searchSpec: GuitarSpec): List<Instrument> = inventory.filter { it.instrument.matches(searchSpec) }
}

fun printGuitars(guitars: List<Instrument>){
    if(guitars.isNotEmpty()){
        guitars.forEach { guitar ->
            val spec = guitar.instrument
            println("""
                =====================================================================
                Erin, you might like this ${spec.builder} ${spec.model}
                ${spec.type} guitar: ${spec.backWood} back and sides
                ${spec.topWood} top you can have it for only ${guitar.price}
                =====================================================================
                """.trimIndent())
            println()
        }
    } else println("Sorry we have nothing for you")
}

fun main() {
    val inventory = Inventory()
    inventory.addInstrument("V956935", 1499.95, GuitarSpec(Builder.FENDER, "Stractocastor1", Type.ELECTRIC,Wood.MAPLE, Wood.MAPLE, 12))
    inventory.addInstrument("V956936", 1433.11, MandolinSpec(Builder.FENDER, "Stractocastor2", Type.ELECTRIC,Wood.MAPLE, Wood.MAPLE, Style()))
    inventory.addInstrument("V956937", 1433.11, InstrumentSpec(Builder.FENDER, "Stractocastor3", Type.ELECTRIC,Wood.MAPLE, Wood.MAPLE))
    val erinLikes = GuitarSpec(Builder.FENDER, "Stractocastor1", Type.ELECTRIC,Wood.MAPLE, Wood.MAPLE, 12)
    val guitars = inventory.search(erinLikes)
    printGuitars(guitars)
}