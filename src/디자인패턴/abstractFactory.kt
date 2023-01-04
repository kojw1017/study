package 디자인패턴

abstract class Product{
    abstract fun use()
}
abstract class Factory{
    fun create(owner: String): Product{
        val p: Product = createProduct(owner)
        registerProduct(p)
        return p
    }
    abstract fun createProduct(owner: String): Product
    abstract fun registerProduct(product: Product)
}

class IDCard(private val owner: String): Product(){
    init { println("$owner 의 카드를 만듭니다.") }
    override fun use() = println("$owner 의 카드를 사용합니다")
    fun getOwner() = owner
}

class IDCardFactory: Factory(){
    private val owners = mutableListOf<String>()
    override fun createProduct(owner: String): Product = IDCard(owner)
    override fun registerProduct(product: Product) {
        owners.add((product as IDCard).getOwner())
    }
}

fun main(){
    val factory: Factory = IDCardFactory()
    val card1: Product = factory.create("고정욱")
    val card2: Product = factory.create("엄지민")
    val card3: Product = factory.create("김선우")
    card1.use()
    card2.use()
    card3.use()
}