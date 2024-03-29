package 오브젝트

enum class TestEnum(val title: String){
    AA("시발AA"),
    BB("시발BB"),
    CC("시발CC"),
    DD("시발DD"),
    EE("시발EE"),
    FF("시발FF"),
    GG("시발GG");
    companion object{
        fun defaultList() = listOf(AA, BB, CC, DD)
    }
}
class TestIterator<T: Any>(val first: T, val list: List<T>):Iterable<T>{
    inner class Iter:Iterator<T>{
        private var cursor = 0
        override fun hasNext() = cursor < list.size + 1
        override fun next():T{
            val curr = cursor
            cursor++
            return if(curr == 0) first else list[curr - 1]
        }
    }
    override operator fun iterator():Iterator<T> = Iter()
    operator fun get(index:Int):T{
        return if(index == 0) first else list[index - 1]
    }
}

fun main(){
    val iter = TestIterator(TestEnum.GG, TestEnum.defaultList()).iterator()
    iter.forEach {
        println(it)
    }
}