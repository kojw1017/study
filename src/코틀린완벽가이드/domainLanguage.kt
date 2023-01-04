import java.util.Random
import java.util.StringJoiner
import kotlin.properties.Delegates.notNull
//class CartItem(data: Map<String, Any?>){
//    val title: String by data
//    val price: String by data
//    val qty: String by data
//}
//operator fun String.times(n: Int) = repeat(n)
//enum class Color{
//    BLACK, WHITE, RED, GREEN;
//    operator fun not() = when(this){
//        BLACK -> WHITE
//        WHITE -> BLACK
//        RED -> GREEN
//        GREEN -> RED
//    }
//}
interface ResultSet<out T>{
    val items: Sequence<T>
}
class From<out T>(private val source: Iterable<T>): ResultSet<T>{
    override val items: Sequence<T>
        get() = source.asSequence()
}
class Where<out T>(
    private val from: ResultSet<T>,
    private val condition: (T) -> Boolean
):ResultSet<T>{
    override val items: Sequence<T>
        get() = from.items.filter(condition)
}
class Select<out T, out U>(
    private val from: ResultSet<T>,
    private val output: (T) -> U
):ResultSet<U>{
    override val items: Sequence<U>
        get() = from.items.map(output)
}
class OrderBy<out T, in K: Comparable<K>>(
    private val select: ResultSet<T>,
    private val orderKey: (T) -> K
):ResultSet<T>{
    override val items: Sequence<T>
        get() = select.items.sortedBy(orderKey)
}
infix fun<T> From<T>.where(condition: (T) -> Boolean) = Where(this, condition)
operator fun <T> ((T) -> Boolean).not():(T) -> Boolean = { !this(it) }
fun main(){
    fun isShort(s: String) = s.length <= 4
    fun String.isUpperCase() = all { it.isUpperCase() }
    val data = listOf("2234","32345","423","5234","62345")
    println(data.count{!isShort(it)})
    println(data.count{!it.isUpperCase()})
    var text: String by notNull()
    fun readText(){ text = readLine()!! }
//    val a = mutableMapOf()

}
/*
    1-1. 연산자 오버로딩이란 무엇이고
    - 코틀린 내장 연산자에 대해 새로운 의미를 부여할 수 있게 해주는 언어기능
    - 객체끼리 더하거나 뺄 때, 원하는 동작을 함수 안에 구현하면 연산자를 통해 표현 가능

    1-2. 코틀린에서는 어떤 관습을 사용해 연산자 오버로딩을 사용하는가?
    - 연산자를 오버로딩하는함수 앞에는 함수명 앞에 operator 키워드를 붙여야하며 operator 키워드를 붙인다.

    2. 표준 위임 구현을 설명하라
    - 코틀린 표준 라이브러리에서는 몇 가지 유용한 위임 구현이 있다.
    - 이런 위임을 통해 프로퍼티를 읽고 쓰는 방법을 정의할 수 있고, 위임 객체 자체의 생성을 제어할 수 있다.
    - 다중 스레드 환경에서 lazy를 통한 지연 계산 프로퍼티 동작을 제어하는 방법
    - 세 가지 스레드 안전성 모드가 있다
        - SYNCHRONIZED
        - PUBLICATION
        - NONE
        -
    - 코틀린에서 제공하는 표준 위임 종류로는,
    - lazy - 지연 초기화를 하는 lazy는 프로퍼티의 초기화를 개체 생성 때 하지 않고 필요할 때 초기화하는 것
    - notNull - 프로퍼티 초기화를 미루면서 널이 아닌 프로퍼티를 정의할 수 있게 해주는 것
    - observable - 프로퍼티 값이 변경될 때 통지를 받을 수 있게 해주는 것. 초깃값과 람다를 받고 프로퍼티 값이 변경될 때마다 람다가 호출된다.
    - vetoable - 초기값과 Boolean을 반환하는 람다를 인자로 받고, 프로퍼티 값을 변경하려고 시도할 때마다 람다를 호출하고, true를 반환하면 실제 값 변경이 일어난다.
    - observable과 vetoable을 조합하고 싶다면 ObservableProperty를 상속해서 beforeChange와 afterChange함수를 오버라이드 하면 된다.

    3-1. 프로퍼티 위임의 관습은 무엇인가?
    - getValue() 함수와 setValue()를 제공함으로써 객체의 속성과 지역변수를 읽고 쓰는 요청을 가로챌 수 있다.
        - getValue의 첫번째 인자는 수신 객체 값이 들어있고, 위임된 프로퍼티의 수신객체와 같은 타입 또는 상위 타입이어야 한다
        - getValue의 두번째 인자는 프로퍼티 선언을 표현하는 리플렉션이 들어있어야 한다.
        - setValue는 getValue인자 두 개에다가 프로퍼티에 저장할 새 값까지 해서 총 세개의 인자가 필요하다.

    3-2. 커스텀 위임 구현의 한 가지 예를 들어보라

    4. 런타임에 리플렉션을 써서 위임 값에 접근하는 방법을 설명하라
    런타임에 위임은 별도 필드에 저장
    프로퍼티 자체에 대해서는 접근자가 자동으로 생성된다
    리플렉션 api 사용하면 getDelegate()멤버를 통해 접근 가능
    위임 인스턴스가 저장된 비공개 필드에 접근하려면 isAccessible = true 사용해야함

    확장으로 정의된 프로퍼티는 getExtensionDelegate()사용해서 위임을 얻을 수 있다

    5. 고차 함수를 사용해 DSL을 설계하는 방법을 설명
    예제에서는 데이터 원소의 시퀀스를 돌려주는 공통 인터페이스를 만들고
    이 인터페이스를 상속받은 클래스들을 정의한다
    다음으로 이들을 엮어줄 중위 연산자 함수를 infix 키워드를 써서 정의하고
    질의를 시작 타입 안전성으로 인해 의도한 문법에 맞지않는 구조는 컴파일 에러가 난다

    계층적 구조의 DSL 설계 방법
    ContainerBuilder클래스를 만들고 필요한 메소드를 정의한다
    panel과 dialog함수는 람다를 받고 이 람다는 ContainerBuilder클래스의 확장 함수 역할을 한다
    이렇게 하면 내부에서 this 수신 객체로 ContainerBuilder가 암시적으로 지정되기 때문에 멤버 호출 가능

    패널의 경우 자식을 순서대로 추가하기 위해 BoxLayoutBuilder에 unaryPlus()를 추가했다
    자식 컴포넌트를 유지했다가 컨테이너에 연결해주는 프로퍼티 설정 후 observable 위임을 사용해 별도로 처리

    6. @DslMarker의 의미를 설명
    메타 에너테이션으로 애너테이션을 정의할때 쓰이고
    이중 레이아웃 예제에서 수신 객체의 영역을 제한할 수 있게 해주는 것

    6.@DslMarker의 의미를 설명
    메타 에너테이션으로 마커 애너테이션을 정의할때 쓰이고,
    여러 바깥의 블록의 this에 접근 못하게 통제하게 하여,
    암시적 수신 객체의 영역을 제한할 수 있게 한다.'

    DSL이란 뭘까 개발자가 아니라 다른 사람도 그 함수를 사용할 수 있게 해주는것것

@Retention(AnnotationRetention.SOURCE)
@DslMarker
annotation class LayoutDsl
@Retention(AnnotationRetention.BINARY)
@DslMarker
annotation class LayoutDsl
When applied to annotation class X specifies that X defines a DSL language
The general rule:
an implicit receiver may belong to a DSL @X if marked with a corresponding DSL marker annotation
two implicit receivers of the same DSL are not accessible in the same scope
the closest one wins
other available receivers are resolved as usual, but if the resulting resolved call binds to such a receiver, it's a compilation error
Marking rules: an implicit receiver is considered marked with @Ann if
its type is marked, or
its type's classifier is marked
or any of its superclasses/superinterfaces

 */