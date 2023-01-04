annotation class MyAnnotation
class  A @MyAnnotation constructor()
annotation class Dependency(val arg: String, val componentNames: String="Core")
annotation class Component(val name: String="Core")
@Component("I/O")
class IO
@Component("Log")
@Dependency("I/O")
class Logger

fun main(){
    val objects = mutableListOf("123")
    val s = @Suppress("UNCHECKED_CAST") objects as List<String>
    println(s)
}

/*
    1. 새로운 애너테이션을 어떻게 정의하는가 코틀린과 자바 비교
    애너테이션의 장점
    아무대나 붙여도 코드에 영향을 주지 않음
    애너테이션을 왜썼냐> 에너테이션 프로세서 이용해서 에너테이션에 대한 동작을 해줄 수 있음
    프로그램의 구조 삼중화 가능
    @애너테이션 이름
    애너테이션 정의하려면 annotation 변경자를 붙여야한다 annotation class MyAnnotation
    - 주생성자에 적용하고 싶다면 주생성자 인자 목록 앞에 붙인다
    class  A @MyAnnotation constructor()
    - 자바는 애너테이션을 인터페이스로 구성 코틀린은 특별한 클래스로 구성
    - 애너테이션 클래스에는 멤버나 부생성자, 초기화 코드가 없음
    - 1.3부터 내포된 클래스, 인터페이스, 객체(동반 객체)를 애너테이션 본문에 넣을 수 있다
    - 코틀린은 식에도 애너테이션을 적용할 수 있다
    2. 코틀린 코드에 애너테이션을 어떻게 붙일 수 있나
    3. 코틀린이 제공하는 내장 애너테이션 설명
    4. 애너테이션 사용 지점 대상은 무엇인가, @Target메타 애너테이션과 사용지점 대상 사에에는 어떤 연관이 있나
    애너테이션 사용지점 대상은 397p에 나와있는 다양한 프로퍼티 구성 요소들이고
    @Target 메타 어노테이션은 어노테이션을 만들때 해당 어노테이션을 어떤요소에 붙일수 있는지 지정해주는것
    5. 코틀린 리플렉션 API구성하는 기본타입
    6.클래스 리터럴과 호출 가능 참조 구문을 설명하라.
    - 클래스 리터럴
    - ::class 구문을 사용하면 KClass 타입의 리플렉션 객체로 클래스에 대한 표현을 얻을 수 있다. KClass 타입은 자바 언어의 Class 타입에 해당하는 코틀린 클래스다.
    - 호출 가능 참조 구문
    - 호출가능요소(Callble)라는 개념은 어떤 결과를 얻기 위해 호출할 수 있는 함수나 프로퍼티를 함께 묶어준다. 리플렉션 API에서는 KCallable<out R>이라는 제네릭 인터페이스를 통해 호출 가능 요소를 표현한다. 여기서 R은 함수의 반환 타입이거나 프로퍼티 타입에 해당한다.
    - KClass 인스턴스로부터는 최상위 호출 가능 인스턴스를 얻을 수 없다.
    7. KClass API설명, KClass와 자바 Class 인스턴스를 어떻게 상호 변환하나
    - KClassifier 의 하위타입
    - KClass<T>: 컴파일 시잠에 T 타입인 클래스나 인터페이스, 객체 선언을 런타임에 표현한다.
    - KClass 인스턴스를 얻는 방법1. 클래스 리터럴 구문을 사용하는 것
    - `println(String::class.isFinal)`
    - KClass 인스턴스를 얻는 방법2. **kotlin 확장 프로퍼티**를 사용해 java.lang.Class의 인스턴스를 KClass로 변환하는 것이다. 전체 이름을 갖고 클래스를 동적으로 찾을 때 유용하다.
    - `val stringClass = Class.forName("java.lang.Strkng").kotlin`
    KClass 와 자바의 Class 인스턴스를 어떻게 상호 변환하는가?
    단하게는 class refrences기능을 사용하여 클래스 정보를 가져올 수 있는데, 가져온 인스턴스의 타입은 자바와 다른 KClass타입입니다. 여기서 java class 객체를 얻으려면 java 확장 속성을 사용하십시오
    8. KCallable API 설명
 */