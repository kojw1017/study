package 디자인패턴.ab

/*
    팩토리 메소드
    인터페이스 부분과 생성 부분을 완전히 분리하여 관리 할 수 있다
 */
//생성 인터페이스 부분
interface Item{ fun use() }
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

//생성 구현 부분
class Sword: Item { override fun use() = println("칼로 베었다") }
class Shield: Item { override fun use() = println("방어 했다") }
class Bow: Item { override fun use() = println("활로 쐈다") }
class ItemFactory: Factory(){
    private class ItemData(var maxCount: Int){
        var currentCount: Int = 0
    }
    private var repository = hashMapOf("sword" to ItemData(3), "shield" to ItemData(2), "bow" to ItemData(1))
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
    val factory = ItemFactory()
    val item = factory.create("sword")
    item?.use()
}