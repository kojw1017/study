
val trim = """[^.\d-+*/]""".toRegex()
val groupMD = """((?:\+|\+-)?[.\d]+)([*/])((?:\+|\+-)?[.\d]+)""".toRegex()
fun trim(v: String) = v.replace(trim, "")
fun repMtoPM(v: String) = v.replace("-", "+-")
fun foldGroup(v: String) = groupMD.findAll(v).fold(0.0){ acc, curr ->
    val (_, left, op, right) = curr.groupValues
    println(left)
    println(right)
    val leftVal = left.replace("+", "").toDouble()
    val rightVal = right.replace("+", "").toDouble()
    val result = when(op){
        "*" -> leftVal * rightVal
        "/" -> leftVal / rightVal
        else -> throw Throwable("")
    }
    acc + result
}
fun main(){
    println(foldGroup(repMtoPM(trim("5*3+10"))))
}