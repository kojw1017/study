package 코틀린디자인패턴

interface Item{ fun use() }
class Sword: Item { override fun use() = println("칼로 베었다") }
class Shield: Item { override fun use() = println("방어 했다") }
class Bow: Item { override fun use() = println("활로 쐈다") }

abstract class Factory{
    fun create(name: String): Item?{
        if(isCreatable(name)){
            val item = createItem(name)
            postprocessItem(name)
            return item
        }
        return null
    }
    abstract fun isCreatable(name: String): Boolean
    abstract fun createItem(name: String): Item
    abstract fun postprocessItem(name: String)
}
class ItemFactory: Factory(){
    private class ItemData(var maxCount: Int){
        var currentCount: Int = 0
    }
    private var repository: HashMap<String, ItemData> = hashMapOf("sword" to ItemData(3),
        "shield" to ItemData(3),
        "bow" to ItemData(3))
    override fun isCreatable(name: String): Boolean {
        val itemData = repository[name]
        if(itemData == null){
            println("$name 은 알 수 없는 아이템입니다")
            return false
        }
        if(itemData.currentCount >= itemData.maxCount){
            println("$name 은 품절 아이템 입니다")
            return false
        }
        return true
    }
    override fun createItem(name: String): Item {
        return when(name){
            "sword" -> Sword()
            "shield" -> Shield()
            "bow" -> Bow()
            else -> Bow()
        }
    }
    override fun postprocessItem(name: String) {
        repository[name]?.let { it.currentCount++ }
    }
}

fun main(){
    val factory:Factory = ItemFactory()
    val item1 = factory.create("sword")
    item1?.use()
    val item2 = factory.create("sword")
    item2?.use()
    val item3 = factory.create("sword")
    item3?.use()
    val item4 = factory.create("sword")
    item4?.use()
}