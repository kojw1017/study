
class TreeNode<T>(val data: T){
    private val _children = arrayListOf<TreeNode<T>>()
    var parent: TreeNode<T>? = null
    private set
    val children: List<TreeNode<T>> get() = _children
    fun addChild(data: T) = TreeNode(data).also {
        _children += it
        it.parent = this
    }

    override fun toString() = _children.joinToString(prefix = "$data {", postfix = "}")
}

fun <T>TreeNode<T>.cancellabelWalkDepthFrist(onEach: (T) -> Boolean): Boolean{
    val nodes = java.util.LinkedList<TreeNode<T>>()
    nodes.push(this)
    while (nodes.isNotEmpty()){
        val node = nodes.pop()
        println(node.data)
        if(!onEach(node.data)) return false
        node.children.forEach{ nodes.push(it)}
    }
    return true
}
inline fun <reified T> TreeNode<*>.isInstanceOf() = cancellabelWalkDepthFrist { it is T }

annotation class MyAnnotation
class  A @MyAnnotation constructor()

fun main(){
    val tree = TreeNode<Any>("abc").addChild("def").addChild("Awd")
    val objects = mutableListOf("123")
    val s = @Suppress("UNCHECKED_CAST") objects as List<String>
    println(s)
}
/*
    1. 새로운 애너테이션을 어떻게 정의하는가 코틀린과 자바 비교
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
    5. 코틀린 리플렉션 API구성하는 기본타입
    6. 클래스 리터럴과 호출 가능 참조 구문 설명
    7. KClass API설명, KClass와 자바 Class 인스턴스를 어떻게 상호 변환하나
    8. KCallable API 설명
 */