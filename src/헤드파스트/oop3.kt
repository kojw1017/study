//package 헤드파스트
//
///*
//501p
//*/
//class Board(var width: Int, var height: Int, var tiles: MutableList<MutableList<Tile>>){
//    init {
//        tiles = mutableListOf()
//        (0 until width).forEach{ i ->
//            tiles.add(mutableListOf())
//            (0 until height).forEach{ j ->
//                tiles[i].add(Tile())
//            }
//        }
//    }
//    fun getTile(x: Int, y: Int) = tiles[x-1][y-1]
//    fun addUnit(unit: Unit, x: Int, y: Int){
//        getTile(x, y).addUnit(unit)
//    }
//    fun removeUnit(unit: Unit, x: Int, y: Int){
//        getTile(x, y).removeUnit(unit)
//    }
//    fun removeUnits(x: Int, y: Int){
//        getTile(x, y).removeUnits()
//    }
//    fun getUnits(x: Int, y: Int) = getTile(x, y).getUnits()
//}
//
//class Tile(var units: MutableList<Unit> = mutableListOf()){
//    fun addUnit(unit: Unit){
//        units.add(unit)
//    }
//    fun removeUnit(unit: Unit){
//        units.remove(unit)
//    }
//    fun removeUnits(){
//        units.clear()
//    }
//    fun getUnits() = units
//}
//class Unit(
//    var type: String,
//    val id: Int,
//    var name: String,
//    private val waepons: MutableList<Weapon> = mutableListOf(),
//    private var properties: HashMap<String, Any> = hashMapOf()){
//    fun addWeapon(weapon: Weapon){
//        waepons.add(weapon)
//    }
//    fun getWeapons() = waepons
//    fun setProperty(property: String, value: Any){
//        properties[property] = value
//    }
//    fun getProperty(property: String) = properties[property]?: "프로퍼티가 없습니다"
//}
//class Weapon
//fun main() {
//
//}