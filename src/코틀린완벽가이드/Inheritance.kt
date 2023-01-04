class Address(
    val city: String,
    val street: String,
    val house: String
){
    override fun equals(other: Any?): Boolean {
        if(other !is Address) return false
        return city == other.city && street == other.street && house == other.house
    }
}
open class Entity(
    val name: String,
    val address: Address
)
class Person(
    name: String,
    address: Address,
    val age: Int
): Entity(name, address)

class Organization(
    name: String,
    address: Address,
    val manager: Person
): Entity(name, address)

interface Vehicle{
    val currentSpeed: Int
    fun move()
    fun stop()
}
interface FlyingVegicle: Vehicle{
    val currentHeight: Int
    fun takeOff()
    fun land()
}
class Car: Vehicle{
    override var currentSpeed = 0
    override fun move() {
        println("Roding")
        currentSpeed = 50
    }
    override fun stop() {
        println("Stopped")
        currentSpeed = 100
    }
}
class Aircraft() : FlyingVegicle{
    override var currentSpeed = 0
    override var currentHeight = 0
    override fun stop() {
        println("Stopped")
        currentSpeed = 0
    }
    override fun move() {
        println("Taxiing")
        currentSpeed = 50
    }
    override fun land() {
        println("land")
        currentSpeed = 50
        currentHeight = 0
    }
    override fun takeOff() {
        println("TakeOff")
        currentSpeed = 500
        currentHeight = 5000
    }
}
fun main(){
    val address = arrayOf(Address("London", "ive", "Ba"),
        Address("New York", "iv22e", "Ba33"),
        Address("Sydney", "ive44", "Ba4"))
    println(address.indexOf(Address("New York", "iv22e", "Ba33")))
}
/*
 1.코틀린에서 하위클래스 어떻게 정의 하는가? 클래스를 상속할 수 있게 만들려면 어떤 조건 만족 시켜야 하나
 : 뒤에 상위 클래스가 될 이름을 넣어주면 된다 상위클래스는 class앞에 open을 붙여준다

 데이터 클래스 항상 final이며 1.1버전부터 다른 클래스를 상속받을 수 있다
 인라인 클래스는 다른 클래스를 상속할 수 없고 상위 클래스 역할도 할 수 없다
 2.자바와 코틀린 클래스 상속의 차이점
 자바는 extends나 implements같은 특별한 키워드 사용 코틀린은 필요없고
 자바는 모든 클래스가 디폴트로 열려있고 상속을 금지하려면 final을 명시해아함 코틀린에서는 디폴트가 final
 3.클래스가 초기화 되는 과정 설명 자바에서는 어떻게 강제하는지 코틀린과 자바 비교
 상위 클래스부터 하위 클래스 순으로 초기화된다 상위클래스 초기화 상태가 하위 클래스에 영향을 미치기 때문에
 4.is/as/as? 연산자의 목적 스마트 캐스팅 코틀린과 자바의 차이
 null을 취급하는 방법이 다르다 자바는
 is null에대해 항상 false를 반환하지만 코틀린은 널이될수있는지 여부에 따라 달라진다
 as 강제 변환시 null은 항상 null올 캐스팅 되지만 코틀린은 널 가능성에 따라 예외가 발생될수도있고 null이될수도있다
 5.Any클래스에 정의된 공통 메서드 나열하고 각 메서드 구현할 때 지켜야 할 기본 지침
 equals 동등성비교 기본적으로 참조 동등성만 구현하지만 equals메소드 오버라이드해서 내용을 바탕으로 동등성을 비교하게 구현하면  구조적 동등성도 구현가능
 equals  구현의 일반적인 요구사항은
 널이아닌 객체가 널과 같을 수 없고
 모든 객체는 자기 자신과 동등해야 하고
 대칭적이어야 하고 추이적 이어야 한다
 6.추상클래스와 추상 클래스 멤버란 무엇인가 추상 클래스나 추상 클래스 멤버를 처리하는 규칙은 무엇인가
 추상클래스 : 상위 클래스 역할만 하는 클래스
 생성자 있을 수 있다 이생성자는 위임 호출로만 호출됨
 추상 클래스 멤버는 세부구현을 생략한 멤버 하위클래스는 이를 오버라이드해서 구현부를 만든다
 프로퍼티 초기화 불가 접근자나 by절 사용불가 추상함수 본문 ㅇ벗음 명식적으로 반환타입을 적는다 타입추론 불가하기 때문
 7.추상클래스와 인터페이스의 차이 코틀린과 자바 비교
 생성자를 만들 수 없고 인터페이스의 멤버는 기본이 추상멤버다
 자바와달리 상속할때 :쓰고 인터페이스명을 쓰면 된다
 인터페이스는 다중 상속 가능
 8.인터페이스 상속 어던식으로 하는지 클래스 멤버를 오버라이딩 할때와 인터페이스 멤버를 오버라이딩 할때
 차이점 인터페이스는 오버라이딩을 다해줘야하고 클래스 멤버는 선택적으로 할 수 있다
 동일한 시그니처의 멤버를 가지는 인터페이스를 둘이상 상속 할땐는 명시적으로 구현하도록 강제한다
 9.봉인된 클래스 계층은 무엇인가 자바를 사용한다면 봉인된 클래스 계층을 어떤 식으로 상속 할 수 있는가
 기본 가시성은 public, open이다.
    Sealed class는 추상 클래스이고, 객체화할 수 없으며 private 생성자를 갖고 있다.
    Sealed class의 하위 클래스들은 동일한 파일에 정의되어야 한다. 서로 다른 파일에서 정의할 수 없다.
    Sealed interface도 있다.(2020.02.22 기준으로 kotlin 1.5 시험 버전에서 사용 가능한 상태이다.)
    Sealed class를 사용하면 타입 분기로 사용되는 when이 반드시 값을 반환해야 하고, Sealed class타입을 인자로 받을 때, 나눠지는 조건은 Sealed class의 하위 클래스 타입 전부이어야하며, else가 없어도 정상 작동한다.

Enum class의 특징
Enum class는 상속해줄 수 없으며 상속 받을 수도 없다. 그러나 interface를 구현할 수 있다.
일반 클래스처럼 프로퍼티, 메서드, 생성자를 가질 수 있다.
상수 객체와 메서드, 프로퍼티의 기본 가시성은 public이고, 생성자의 기본 가시성은 private이다.
Enum class는 객체화할 수 없으며 private 생성자만을 정의할 수 있다.
Enum class의 상수들은 그 상수를 포함하고 있는 Enum class 타입이다.
자동으로 생성해주는 메서드와 프로퍼티가 있다.(values(), valueOf(), ordinal, name, toString(), compareTo() 등)

 10. 코틀린에서 클래스 위임이 어떻게 작동하는지 설명하라
 인터페이스를
*/