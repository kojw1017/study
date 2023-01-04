package 디자인패턴.a

interface Product: Cloneable{
     fun use(s: String)
     fun createClone(): Product
}

class Manager{
    private val showCase: HashMap<String, Product> = hashMapOf()
    fun register(name: String, proto: Product){
        showCase[name] = proto
    }
    fun create(protoName: String): Product?{
        val p = showCase[protoName]
        return p?.createClone()
    }
}

class MessageBox(val decoChar: String): Product{
    override fun use(s: String) {
        val length = s.length.toByte()
        for (i in 0 until length + 4){ print(decoChar) }
        println("")
        println("$decoChar $s $decoChar")
        for (i in 0 until length + 4){ print(decoChar) }
        println("")
    }
    override fun createClone(): Product {
        var p: Product? = null
        try { p = clone() as Product }
        catch (e: CloneNotSupportedException) { e.printStackTrace() }
        return p!!
    }
}

class UnderlinePen(private val ulChar: String): Product{
    override fun use(s: String) {
        val length = s.length.toByte()
        println("\\ $s  \\")
        print("")
        for (i in 0..length){ print(ulChar) }
        println("")
    }

    override fun createClone(): Product {
        var p: Product? = null
        try { p = clone() as Product }
        catch (e: CloneNotSupportedException) { e.printStackTrace() }
        return p!!
    }
}
fun main(){
    val manager = Manager()
    val upen = UnderlinePen("~")
    val mBox = MessageBox("*")
    val sBox = MessageBox("/")

    manager.register("strong message", upen)
    manager.register("warning box", mBox)
    manager.register("slash box", sBox)

    manager.create("strong message")?.also { it.use("hello world") }
    manager.create("warning box")?.also { it.use("hello world") }
    manager.create("slash box")?.also { it.use("hello world") }
}