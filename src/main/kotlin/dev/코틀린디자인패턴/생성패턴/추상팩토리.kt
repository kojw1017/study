package 코틀린디자인패턴.생성패턴
interface Property{
    val name:String
    val value:Any
}
interface ServerConfiguration{
    val properties:List<Property>
}
//interface Parser{
//    fun property(prop: String):Property
//    fun server(propertyStrings: List<String>):ServerConfiguration
//}
data class PropertyImpl(
    override val name:String,
    override val value: Any
): Property

data class StringPropertyImpl(
    override val name:String,
    override val value: String
): Property
data class IntPropertyImpl(
    override val name:String,
    override val value: Int
): Property
data class ServerConfigurationImpl(
    override val properties: List<Property>
): ServerConfiguration

class Parser{
    companion object{
        fun property(prop:String): Property {
            val (name, value) = prop.split(":")
            return when(name){
                "port" -> IntPropertyImpl(name, value.trim().toInt())
                "environment" -> StringPropertyImpl(name, value.trim())
                else -> throw RuntimeException("알수 없는 속성:$name")
            }
        }
        fun server(propertyStrings: List<String>): ServerConfiguration {
            val parseProperties = mutableListOf<Property>()
            for(p in propertyStrings){
                parseProperties += property(p)
            }
            return ServerConfigurationImpl(parseProperties)
        }
    }
}
fun main(){
    val portProperty = Parser.property("port:8080")
    val environmentProperty = Parser.property("environment:production")
    val port:Int = if(portProperty is IntPropertyImpl) portProperty.value else 0
}