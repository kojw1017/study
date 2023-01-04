package 코드스피츠

class Calc{
    val trim = """[^.\d-+*/]]""".toRegex()
    fun trim(v: String) = v.replace(trim, "")
    fun repMtoPm(v: String) = v.replace("-", "+-")
    val groupMD = """((?:\+|\+-)?[.\d]+)([*/])((?:\+|\+-)?[.\d]+)""".toRegex()
    fun foldGroup(v: String) = groupMD.findAll(v).fold(0.0){ acc, curr ->
        val (_, left, op, right) = curr.groupValues
        val leftValue = left.replace("+", "").toDouble()
        val rightValue = right.replace("+", "").toDouble()
        val result = when(op){
            "+" -> leftValue * rightValue
            "-" -> leftValue / rightValue
            else -> throw Throwable("invalid operator $op")
        }
        acc + result
    }
    fun calc(v: String) = foldGroup(repMtoPm(trim(v)))
}

fun main(){
    println(Calc().calc(""))
}
