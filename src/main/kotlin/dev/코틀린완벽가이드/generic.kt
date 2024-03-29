class Stack<E>(vararg items: E){
    private val elements = items.toMutableList()
    fun push(element: E){
        elements.add(element)
    }
    fun pop(): E?{
        if(!isEmpty()) return elements.removeAt(elements.size -1)
        return null
    }
    private fun isEmpty(): Boolean{
        return elements.isEmpty()
    }
}

fun <T> stackOf(vararg elements: T): Stack<T> = Stack(*elements)

interface Source<out T>{
    fun nextT(): T
}
fun demo(strs: Source<String>){
    var objects: Source<Any> = strs
}

interface List1<out T>{
    val size: Int
    fun get(index: Int): T
}
class ListByArray<T>(private vararg val items: T): List1<T>{
    override val size get() = items.size
    override fun get(index: Int) = items[index]
}
class Writer<in T>{
    fun write(value: T){
        println(value)
    }
    fun writeList(values: Iterable<T>){
        values.forEach { println(it) }
    }
}
fun <T> concat(list1: List1<T>, list2: List1<T>) = object: List1<T>{
    override val size: Int
        get() = list1.size + list2.size

    override fun get(index: Int): T {
        return if(index < list1.size){
            list1.get(index)
        }else{
            list2.get(index - list1.size)
        }
    }
}
class TreeNode<T>(val data: T){
    private val _children = arrayListOf<TreeNode<T>>()
    var parent: TreeNode<T>? = null
        private set
//    val children: List<TreeNode<T>> get() = _children
    fun addChild(data: T) = TreeNode(data).also {
        _children += it
        it.parent = this
    }

    override fun toString() = _children.joinToString(prefix = "$data {", postfix = "}")
}
var <T> TreeNode<T>.death: Int
    get() = 1
    set(value) = TODO()


fun main(){
    val numbers = ListByArray(1, 2, 3 , 4.2)
    val integers = ListByArray(5, 6, 7, 8, 6)
    val result = concat(numbers, integers)

    val numW = Writer<Number>()

}

/*
    1. 코틀린에서 제네릭 클래스, 함수, 프로퍼티를 정의하는 방법은 무엇인가
    타입 파라미터를 지정해줘야하는데 클래스는 클래스 이름 뒤에오고 함수는 fun 키워드 뒤에 확장 프로퍼티는 var/val 뒤에
    관습적으로 짧은 대문자를 사용한다 인스턴스를 만들거나 함수를 호출할때는 실제 타입을 지정해야하고 타입추론이 가능하다


    2. 타입 파라미터에 대해 제약을 거는 방법을 설명하라, 자바와 코틀린의 타입 파라미터 제약 기능을 비교
    <T: 상위타입> 코틀린은 상위 바운드만 허용한다

    프로젝션 쓰는거는 둘다 동일
    상위 바운드 설정 둘다 가능
    자바는 클래스 선언시 변성 지점 불가능


   3. 타입 소거란 무엇인가 타입 파라미터의 한계를 일반 타입과 비교해 설명
    제네릭스가 자바5부터 나왔고 JVM 하위 호환성을위해 타입인자에 대한 정보가 지워지기 때문에 타입검사를 할 수 없다
    인터페이스 관계에따라 컴파일 되기도 하지만 위험하기때문에 경고가 표시된다
    원소타입에는 관심없고 그값이 리스트인지만 확인하고 싶다면 스타 프로젝션을 사용해서 써야한다
    타입 체크
    AS는 사용가능 IS는 사용 불가능
    함수에서 is를 사용하고 싶다는 뜻 -> LSP위반 리스코프 치환 원칙
    when(param){
     is X->
     is Y->
    }
    추상 타입으로 일부로 만들었는데 하위타입으로 구분하려는것 이기 때문에 설계에 의문을 가져도 된다
    타입 파라미터는 런타임에 is를 때릴 수 없다

    4. 구체화한 타입 파라미터를 사용해 어떻게 타입 소거를 우회 할 수 있는가 구체화한 타입 파라미터의 한계는 무엇인가
    인라인 함수를 사용해 타입 소거 구체화가능 하지만 컴파일된 코드의 크기가 커질 수 있기 때문에 주의해야하고
    구체화한 타입을 클래스나 프로퍼티와 함께 사용 할 수 없다

    reified
    인라인 함수의 체인이 끊기면 안받아주고
    단순한 형태만 타입 소거 우회를 할 수 있다

    객체지향 에서 생성자만은 반드시 구상 클래스의 것을 써야한다 즉 추상화 할 수 없다
    디자인 패턴의 가장 중요한것 추상 팩토리 메소드 패턴(객체지향에서 유일하게 추상화 안되는걸 추상화 해준다(생성자 추상화))

    어떻게 이방법을 해결하나 DI(의존성주입)가 나중에 해결해
    예를들어 이렇게 사용
    @Singlton
    lateinit var a:Animal


    5. 변성이란무엇인가 제네릭 코드에서 변셩이 중요한 이유는 무엇인가

    변성이란 타입 파라미터에 따라서 제네릭 타입의 관계가 변하는 성질
    불변컬렉션이 아닌이상 무공변 상태이기 때문에 제네릭 코드간 안전한 관계를 만들기 위해서

    기본값 무공변 이고
    일반적인 대표 타입이 내부에서 내부타입을 사용한다

    변성이란 내부타입의 제약을 걸어주는것것

    공변, 반공변의 사용 이유는는 제네타입 내부에서 어떻게 사용할지를 정의하기 위해 필요하다

    6 코틀린에서 선언 지점 변성을 어떻게 사용하는지 설명하라
    상위 타입 파라미터에 OUT이나 IN키워드를 붙여 사용한다


    7. 코틀린 사용 지점 변성과 자바 와일드카드를 비교해 설명하라
    - 코틀린 프로젝션은 자바의 extends/super 와일드카드와 같은 역할을 한다.
    - TreeNode<out Number>와 TreeNode<in Number>는 순서대로 자바의 TreeNode<? extends Number>와 TreeNode<? super Number>에 해당한다.

    함수 내부 맥락에 따라서 변성을 설정해줄 수 있다


    8. 스타 프로젝션의 목적이 무엇인지 설명하라
    아무타입이나 될수있게 만드는것


    9. 타입 별명 구문을 설명하라 임포트 별명이나 상속과 같은 언어 기능과 타입 별명을 비교하라
    typealias

    전처리기 단순 replace












    1. 코틀린에서 제네릭 클래스, 함수, 프로퍼티를 정의하는 방법은 무엇인가?
    - 클래스, 함수, 프로퍼티 정의할 때 하나 이상의 타입 파라미터를 추가해야 한다.
    - 사용할 때는 타입 파라미터 대신할 실제 타입을 지정해야 한다.
    - 클래스의 타입 파라미터를 각괄호 안에 써야 한다. 타입 파라미터는 클래스 이름 바로 뒤에 온다.

    2-1. 타입 파라미터에 대해 제약을 거는 방법을 설명하라.

    - 바운드를 지정한다.

    2-2. 자바와 코틀린의 타입 파라미터 제약 기능을 비교해 설명하라.

    - 표현 방식이 자바는 T extends Number 이라면, 코틀린에서는 T: Number 이라는점이 다르다.

    3-1. 타입 소거란 무엇인가?

    - 실행 시점에 제네릭 클래스의 인스턴스에 타입 인자의 정보가 들어가지 않는 것
    - 런타임에 제네릭 코드는 파라미터 타입의 차이를 인식할 수 없고, data is T와 같은 검사는 의미 없음.

    3-2. 타입 파라미터의 한계를 일반 타입과 비교해 설명해라.

    -

    4-1. 구체화한 타입 파라미터를 사용해 어떻게 타입 소거를 우회할 수 있는가?

    - 인라인한 함수에 대해서만 구체화한 타입 파라미터를 쓸 수 있게 하는 것인데 ,
    - 함수 본문을 호출 위치로 인라인시켜 컴파일러가 인라인된 함수에 제공되는 타입 인자의 실제 타입을 알게한다.

    4-2. 구체화한 타입 파라미터의 한계는 무엇인가?

    - 인라인 함수 안에서만 구체화한 타입 파라미터를 쓸 수 있기 때문에
    구체화한 타입을 클래스나 프로퍼티와 함께 쓸 수 없다.

    5-1. 변성이란 무엇인가?

    - 파라미터가 달라질 때 제네릭 타입의 하위타입 관계가 어떻게 달라지는 지를 설명하는 제네릭 타입의 측면

    5-2. 제네릭 코드에서 변성이 중요한 이유는?

    - 변성을 합리적으로 사용하면 타입 안전성을 해치지 않으면서 API 유연성을 향상시킬 수 있다.

    6-1. 코틀린에서 선언 지점 변성을 어떻게 사용하는지 설명하라.

    - 타입 파라미터 T 앞에 out이라는 키워드를 붙여 ............................

    7.코틀린 사용 지점 변성과 자바 와일드카드를 비교해 설명하라

    - 코틀린 프로젝션은 자바의 extends/super 와일드카드와 같은 역할을 한다.
    - TreeNode<out Number>와 TreeNode<in Number>는 순서대로 자바의 TreeNode<? extends Number>와 TreeNode<? super Number>에 해당한다.

    8.스타 프로젝션의 목적이 무엇인지 설명하라.

    - 타입 인자가 타입 파라미터의 바운드 안에서 아무 타입이나 될 수 있다는 사실을 표현하기 위한 것

    9-1.타입 별명 구문을 설명하라.

    - 주목적은 제네릭 타입이나 함수 타입처럼 긴 이름을 짧게 부를 수 있도록 해주는 것
    - 타입 별명 정의는 typealias 키워드로 이뤄지며, 그 다음에 별명이 오고 = 기호 다음에 실제 타입이 온다.
    - `typealias IntPredicate = (Int) → Boolean`
    - `typealias IntMap = HashMap<Int, Int>`

    9-2.임포트 별명이나 상속과 같은 (기존 타입 이름에 대한 새로운 타입 이름을 소개하는) 언어 기능과 타입 별명을 비교하라.

    - 임포트 별명은 함수나 프로퍼티에 대한 별명은 지원하지만, 제네릭 별명을 허용하지는 않는다.
    - 제네릭 타입이나 함수 타입을 상속해서 새로운 타입 이름을 제공할 수 있는데, 타입 별명과 차이는 원래 타입의 하위 타입인 새로운 타입을 만들어낸다.?????
 */
