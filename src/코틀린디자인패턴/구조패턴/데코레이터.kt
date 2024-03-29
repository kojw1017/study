package 코틀린디자인패턴.구조패턴

/**
 * 객체간 관계를 다루는 디자인 패턴
 *
 *
 */

interface Coffee{
    fun cost():Int
    fun name():String
}

class NormalCoffee:Coffee{
    override fun cost() = 10
    override fun name() = "coffee"
}

abstract class CoffeeDecorator(private val coffee: Coffee):Coffee{
    override fun cost() = coffee.cost()
    override fun name() = coffee.name()
}
class MilkCoffee(coffee: Coffee):CoffeeDecorator(coffee){
    override fun name() = "Milk ${super.name()}"
}

class MilkCoffee1(coffee: Coffee):CoffeeDecorator(coffee){
    override fun name() = "Milk11 ${super.name()}"
}


fun main() {
    val normalCoffee = NormalCoffee()
    println("${normalCoffee.cost()}")
    println("${normalCoffee.name()}")
    val milkCoffee = MilkCoffee(normalCoffee)
    println("${milkCoffee.cost()}")
    println("${milkCoffee.name()}")
    val milkCoffee1 = MilkCoffee1(normalCoffee)
    println("${milkCoffee1.cost()}")
    println("${milkCoffee1.name()}")
}
