package Adapter

import kotlin.math.pow
import kotlin.math.sqrt

/*
    메멘토 패턴
    객체상태 기억해 두었다가 다시 되돌린다

    객체가
    메멘토 전파 - 기록해야할 필드전부를 가지고 있어야하고 다른 객체를 가져야 할 경우 다른 객체의 필드도 저장해야하기 때문에 전파된다

    메멘토
    상태에 대한 기록 - 메멘토
    기록해야할 모든 필드를 가직고 있어야 한다
    기록해야할 필드가 객체일 경우 구현하기 복잡할수있다

    메멘토 인터페이스를 받은 객체들을 통해 저장할 수 있다
    상태를 저장할 메멘토 객체는 경량화 될수록 좋다

    연산 <-> 메모리
    행위에대한 기록 - 커멘드 패턴

*/

class Walker(private var currentX: Int, private var currentY: Int, private var targetX: Int, private var targetY: Int){
    private var actionList = ArrayList<String>()
    fun walk(action: String) :Double {
        actionList.add(action)
        when (action) {
            "UP" -> {
                currentY += 1
            }
            "RIGHT" -> {
                currentX += 1
            }
            "DOWN" -> {
                currentY -= 1
            }
            "LEFT" -> {
                currentX -= 1
            }
        }
        return sqrt((currentX - targetX).toDouble().pow(2) + (currentY - targetY).toDouble().pow(2))
    }

    class Memento {
        var x = 0
        var y = 0
        var actionList = arrayListOf<String>()
        fun memento(){}
    }

    fun createMemento(): Memento{
        val memento = Memento()
        memento.x = this.currentX
        memento.y = this.currentY
        memento.actionList = this.actionList.clone() as ArrayList<String>
        return memento
    }

    fun restoreMemento(memento: Memento){
        this.currentX = memento.x
        this.currentY = memento.y
        this.actionList = memento.actionList.clone() as ArrayList<String>
    }

    override fun toString() = actionList.toString()


}
fun main() {

}
