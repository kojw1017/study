
fun factCPS(n: Int, block: (Int) -> Unit) = _factCPS(n, 1, block)
fun _factCPS(n: Int, a: Int, block: (Int) -> Unit){
    if(n == 0) block(a) else _factCPS(n-1, n*a, block)
}
fun factThrow(n: Int) = _factThrow(n, 1)
fun _factThrow(n: Int, a: Int) = when {
    n < 0 -> throw Throwable("invalid value $n")
    n == 0 -> a
    else -> 1
}
fun factCPSEX(n: Int, block: (Int) -> Unit, ex:(String) -> Unit = {}) = _factCPSEX(n, 1, block, ex)
tailrec fun _factCPSEX(n: Int, a: Int, block: (Int) -> Unit, ex: (String) -> Unit) {
    when{
        n < 0 -> ex("invalid value $n")
        n == 0 -> block(a)
        else -> _factCPSEX(n-1, n*a, block, ex)
    }
}
fun main(){
    val a = mutableListOf("1", "2")
    var b: MutableList<String>
}