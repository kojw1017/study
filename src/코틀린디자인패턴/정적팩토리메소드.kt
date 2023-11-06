package 코틀린디자인패턴정적팩토리메서드

/**
    정적 팩토리 메서드
    - 생성자와 비교했을때 장점

    생성자에 명시적 이름을 붙일 수 있다(생성자가 많을경우 분리할수있어 유리하다)

    필요한 객체만 생성한다: 정적 팩토리 메서드는 불필요한 객체 생성을 방지할 수 있습니다. 같은 상태를 가진 객체를 요청할 때마다 새로운 객체를 생성하는 대신 이미 생성된 객체를 반환할 수 있습니다(즉, 캐시를 통해 객체를 생성하거나 재사용할 수 있습니다).

    한 객체로부터 다른 객체를 반환할 수 있다: 정적 팩토리 메서드는 많은 유연성을 제공합니다. 이 메서드는 그 메서드가 속한 클래스의 인스턴스를 반환할 필요가 없습니다. 즉, 서브클래스나 관련된 클래스의 인스턴스를 반환할 수 있습니다.

    반환할 클래스를 바꿀 수 있다: 정적 팩토리 메서드는 반환 타입이 인터페이스라면 반환 객체의 클래스를 바꿀 수 있습니다. 이를 통해 클래스의 외부 API는 그대로 유지하면서 내부 구현을 변경하는 유연성을 얻을 수 있습니다.

    - 생성자와 비교했을때 단점

    상속을 원할 경우 문제가 될 수 있다: 하위 클래스로 분리한 경우, 상위 클래스의 정적 팩토리 메서드에는 접근할 수 없습니다. 따라서, 정적 팩토리 메서드를 사용한 클래스는 주로 상속보다 컴포지션을 사용해 확장성을 확보합니다.

    다른 개발자들이 혼동할 수 있다: 정적 팩토리 메서드는 갖춰야 하는 사항이 명확하지 않기 때문에, 다른 개발자들이 클래스를 정상적으로 초기화하지 못하는 경우가 생길 수 있습니다.
 */

data class ChessPiece private constructor(val file: Char, val rank: Char) {
    companion object {
        private val piecesCache = mutableMapOf<String, ChessPiece>()

        fun from(file: Char, rank: Char): ChessPiece {
            val key = "$file$rank"
            //getOrPut 즥이네
            return piecesCache.getOrPut(key) {
                ChessPiece(file, rank).also { piecesCache[key] = it }
            }
        }
    }
}

interface Animal {
    fun speak(): String
}

class Dog : Animal {
    override fun speak() = "Woof!"
}

class Cat : Animal {
    override fun speak() = "Meow!"
}

object AnimalFactory {
    fun createAnimal(type: String): Animal = when (type) {
        "dog" -> Dog()
        "cat" -> Cat()
        else -> throw IllegalArgumentException("Unknown animal type.")
    }
}

fun main() {
    val piece1 = ChessPiece.from('a', '1')
    val piece2 = ChessPiece.from('a', '1')
    val piece3 = ChessPiece.from('b', '1')

    println(piece1 === piece2)  // Outputs: true
    println(piece1 === piece3)  // Outputs: false


    val dog = AnimalFactory.createAnimal("dog")
    println(dog.speak())  // Output: Woof!

    val cat = AnimalFactory.createAnimal("cat")
    println(cat.speak())  // Output: Meow!
}