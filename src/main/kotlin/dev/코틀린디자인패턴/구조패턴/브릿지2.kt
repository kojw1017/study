package 코틀린디자인패턴.구조패턴2

// Weapone 인터페이스 정의
fun interface Weapone {
    fun attack(): Int
}

// K2 객체 정의 (Weapone 인터페이스 구현)
object K2 : Weapone {
    override fun attack(): Int = 2
}

// k3 변수 정의 (Weapone 인터페이스의 익명 구현)
val k3 = Weapone { 5 }

// Rifle 클래스 정의 (Weapone 인터페이스 구현)
class Rifle(val power: Int) : Weapone {
    override fun attack(): Int = power
}

// Leg 인터페이스 정의
fun interface Leg {
    fun move(): Int
}

// regularLeg 변수 정의 (Leg 인터페이스의 익명 구현)
val regularLeg = Leg { 5 }

// AthleticLeg 클래스 정의 (Leg 인터페이스 구현)
class AthleticLeg(val speed: Int, val jump: Int) : Leg {
    override fun move(): Int = speed * jump
}

// StormTrooper 클래스 정의 (Weapone과 Leg 인터페이스를 위임으로 구현)
class StormTrooper(weapone: Weapone, leg: Leg) : Weapone by weapone, Leg by leg

// 메인 함수에서 사용 예시
fun main() {
    val stormTrooper = StormTrooper(k3, AthleticLeg(5, 2))
    println("StormTrooper attack: ${stormTrooper.attack()}")
    println("StormTrooper move: ${stormTrooper.move()}")
}