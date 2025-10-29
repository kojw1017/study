package 코테

class Node<T>(var data: T, var next: Node<T>? = null)

class MyLinkedList<T> {
    var head: Node<T>? = null

    fun add(data: T) {
        val newNode = Node(data)
        if (head == null) {
            head = newNode
            return
        }

        var current = head
        while (current?.next != null) {
            current = current.next
        }
        current?.next = newNode
    }

    override fun toString(): String {
        val sb = StringBuilder()
        var current = head
        while (current != null) {
            sb.append("${current.data} → ")
            current = current.next
        }
        sb.append("null")
        return sb.toString()
    }

    private fun getList(): List<T>{
        val list = mutableListOf<T>()
        var current = head
        while(current != null){
            list.add(current.data)
            current = current.next
        }
        return list
    }

    fun findLast(number:Int): T{
        val list = getList()
        if(number < 0 || number >= list.size){
            throw IndexOutOfBoundsException("Index: $number, Size: ${list.size}")
        }
        return list[list.size - number]
    }
}

/** 🔹 LinkedList<Int>를 정수로 변환하는 확장 함수 */
fun MyLinkedList<Int>.toNumber(): Int {
    val sb = StringBuilder()
    var current = this.head
    while (current != null) {
        sb.append(current.data)
        current = current.next
    }
    return sb.toString().toInt()
}

/** 🔹 두 LinkedList<Int>의 합을 계산하는 확장 함수 */
fun sumOf(list1: MyLinkedList<Int>, list2: MyLinkedList<Int>): Int {
    val num1 = list1.toNumber()
    val num2 = list2.toNumber()
    val sum = num1 + num2
    println("number1: $num1, number2: $num2 = $sum")
    return sum
}

/** 🔹 실행 */
fun main() {
    val linkedList1 = MyLinkedList<Int>().apply {
        add(1); add(2); add(3)
    }

    val linkedList2 = MyLinkedList<Int>().apply {
        add(4); add(5); add(6)
    }

    println(linkedList1) // 1 → 2 → 3 → null
    println(linkedList2) // 4 → 5 → 6 → null

    val sum = sumOf(linkedList1, linkedList2)
    println("합계: $sum")

    println("linkedList1의 마지막에서 두 번째 요소: ${linkedList1.findLast(3)}") // 2
}
