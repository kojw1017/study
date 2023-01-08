package 코틀린완벽가이드

import java.net.ProtocolFamily

/*
    177P
    1. 코틀린 클래스의 기본적인 구조를 설명하라. 자바 클래스와 비교하면 어떤 차이가 있는가?
    간결한 문법 기본적 퍼블릭 공개 가시성
    프로퍼티 get, set 자동 생성
    기본적으로 상속이 막혀있다 final
    상속할때 생성자 만들때 반드시 super생성자 호출 해야함

    2. 주생성자란 무엇인가?
    클래스 파라미터로 인자로 오는 것들

    3. 부생성자란 무엇인가? 클래스에 어떤 생성자(또는 생성자들)를 포함시킬지와 주생성자 외에
    부생성자가 더 필요할지를 어떻게 결정하는가?

    코틀린에서 초기화 순서 주생성자, (속성 초기화, init 블럭), 부생성자 순

    코틀린 클래스에서 init 블럭은 여러번 나올수 있고 어떤 부생성자를 만들어도 먼저 호출함
    여러 생성자가 공통 로직이 필요하다면 private로 메소드 만들필요없이 init 블럭 사용 가능
    모든 생성자가 공통으로 호출하는 메소드 -> init 블럭

    주생성자의 인자로만 필드초기화 가능

    1. 생성자가 받아들인 인자의 타입이 다르면 필수적으로 부생성자 사용 아니면 주생성자의 디폴트 파라미터 사용

    2. 주생성자의 사용 여부는
    필드초기회에 생성자 인자를 바로 활용하고싶거나
    init 블럭 에서도 생성자의 인자를 사용하고 싶을때
    주생성자를 사용하면 부생성자는 반드시 주생성자를 호출해 줘야함

    4. 코틀린이 지원하는 멤버 가시성은 무엇인가? 자바의 가시성과 어떤 차이가 있는가?
    코틀린 public protected internal private
    자바 public protected private default

    코틀린 private : 파일 수준까지 가능
    자바 private :

    코틀린 protected : 다른 패키지라면 상속관계의 클래스만 접근가능
    자바 protected : 같은 패키지라면 패키지내의 모든 클래스에서 가능
    다른 패키지라면 상속관계의 클래스만 접근가능

    코틀린 internal : 모듈내에서 가능
    자바 default :

    5. 내포된 클래스 중에서 내부 클래스와 비내부 클래스의 차이는 무엇인가? 각각에 해당하는 자
    바 클래스와는 어떤 차이가 있는가 비교하라.
    내부 클래스
     class Person(val firstName: String, private val familyName: String){
        inner class P(val description: String){
            fun getOwner() = this@Person.familyName
        }
     }

    비내부 클래스 <- 이걸쓰자그냥
    class Person2(val firstName: String, private val familyName: String){
        class P(val person: Person2, val description: String){
            fun getOwner() = person.familyName
        }
     }
     함수 본문에서는 inner class 만 사용가능




    7. 지연 초기화 메커니즘의 요지는 무엇인가? 널이 될 수 있는 프로퍼티 대신 lateinit 프로퍼티
    를 사용할 경우 어떤 장점이 있는가?
    by lazy 의 편의문법 이다


 */
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