package z

interface SumStrategy{
    fun get(n: Int): Int
}

class GaussSumStrategy: SumStrategy{
    override fun get(n: Int) = (n+1)*n/2
}

class SimpleSumStrategy: SumStrategy{
    override fun get(n: Int): Int {
        var sum = n
        for(i in 1 until n){ sum += i }
        return sum
    }
}

class SumPrinter{
    fun print(strategy: SumStrategy, N: Int){
        print("The Sum of 1 - $N")
        println(strategy.get(N))
    }
}

fun main(){
    val cal = SumPrinter()
    cal.print(SimpleSumStrategy(), 10)
    cal.print(GaussSumStrategy(), 10)
}