package oop

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

class Guitar(
    private var serialNumber: String,
    private var price: Double,
    private var spec: GuitarSpec
){
    fun getSerialNumber() = serialNumber
    fun getPrice() = price
    fun setPrice(newPrice: Double) { price = newPrice }
    fun getSpec() = spec
}

class GuitarSpec(
    private var builder: Builder,
    private var model: String,
    private var type: Type,
    private var numStrings: Int,
    private var backWood: Wood,
    private var topWood: Wood
){
    fun getBuilder() = builder
    fun getModel() = model
    fun getType() = type
    fun getNumStrings() = numStrings
    fun getBackWood() = backWood
    fun getTopWood() = topWood
    fun matches(otherSpec: GuitarSpec): Boolean {
        if(builder != otherSpec.getBuilder()) return false
        if(model.isNotEmpty() && model.lowercase() != otherSpec.getModel().lowercase()) return false
        if(type != otherSpec.getType()) return false
        if(numStrings != otherSpec.getNumStrings()) return false
        if(backWood != otherSpec.getBackWood()) return false
        if(topWood != otherSpec.getTopWood()) return false
        return true
    }
}

class Inventory(private var guitars: MutableList<Guitar> = mutableListOf()) {
    fun addGuitar(serialNumber: String, price: Double, builder: Builder, model: String, type: Type, numStrings: Int, backWood: Wood, topWood: Wood){
        guitars.add(Guitar(serialNumber, price, GuitarSpec(builder, model, type, numStrings, backWood, topWood)))
    }
    fun getGuitar(serialNumber: String): Guitar? = guitars.find { it.getSerialNumber() == serialNumber }
    fun search(searchSpec: GuitarSpec): List<Guitar> = guitars.filter { it.getSpec().matches(searchSpec) }
}

fun main() {
    val inventory = Inventory()
    inventory.addGuitar("V956935", 1499.95, Builder.FENDER, "Stractocastor1", Type.ELECTRIC,6, Wood.MAPLE, Wood.MAPLE)
    inventory.addGuitar("V956934", 1433.11, Builder.COLLINGS, "Stractocastor2", Type.ELECTRIC, 12, Wood.MAPLE, Wood.MAPLE)
    inventory.addGuitar("V956934", 1433.11, Builder.COLLINGS, "Stractocastor2", Type.ELECTRIC, 12, Wood.MAPLE, Wood.MAPLE)
    val erinLikes = GuitarSpec(Builder.COLLINGS, "Stractocastor2", Type.ELECTRIC, 12, Wood.MAPLE, Wood.MAPLE)
    val guitars = inventory.search(erinLikes)
    if(guitars.isNotEmpty()){
        guitars.forEach { guitar ->
            val spec = guitar.getSpec()
            println("""
                =====================================================================
                Erin, you might like this ${spec.getBuilder()} ${spec.getModel()}
                ${spec.getType()} guitar: ${spec.getBackWood()} back and sides
                ${spec.getTopWood()} top you can have it for only ${guitar.getPrice()}
                =====================================================================
                """.trimIndent())
            println()
        }
    } else println("Sorry we have nothing for you")
}