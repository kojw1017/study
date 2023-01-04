package 코틀린완벽가이드
class T(yy:Double=10.0.also { "10.0" }){
    init {
        println("yy = $yy")
    }
    fun foo(){ println("foo: z = $z") }
    var x = 11.also{ println("x: z = $z")}.let { 10 }
    init {
        println("init1")
    }
    var y = 1.also{ println("y: y = y") }
    init {
        foo()
        println("init2: $x, $y")
    }
    var z = 1.also{ println("z: z = z") }
    init {
        foo()
        println("init3: $x, $y, $z")
    }
    init{
        foo()
        println("init4: $x, $y, $z")
    }
    constructor(xx:String):this(10){
        println("constructor1(xx)")
        y = xx.toInt()
    }
    constructor(xx:Int):this(10.1){
        println("constructor2(xx)")
        y =xx
    }
}
fun main(){
    var z = T("11")
}