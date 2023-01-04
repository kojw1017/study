package Adapter

import java.util.Random

/*
    옵저버 패턴
    관찰자가 변경되는 상태를 알려주는..
*/
abstract class Player(private var name: String){
    fun getName() = name
    abstract fun update(diceNumber: Int)
}
abstract class DiceGame{
    protected  var playList = mutableListOf<Player>()
    fun addPlayer(player: Player) { playList.add(player) }
    abstract fun play()
}
class FairDiceGame: DiceGame(){
    private val random = Random()
    override fun play() {
        val diceNumber = random.nextInt(6) + 1
        println("주사위 던졌다 ~ $diceNumber")
        val iter = playList.iterator()
        while (iter.hasNext())
            iter.next().update(diceNumber)
    }
}
class OddBettingPlayer(name: String): Player(name){
    override fun update(diceNumber: Int) { if(diceNumber % 2 == 1) println("${getName()} win") }
}
class EvenBettingPlayer(name: String): Player(name){
    override fun update(diceNumber: Int) { if(diceNumber % 2 == 0) println("${getName()} win") }
}

fun main(){
    val diceGame = FairDiceGame()
    val player1 = EvenBettingPlayer("1번")
    val player2 = OddBettingPlayer("2번")
    val player3 = OddBettingPlayer("3번")
    diceGame.addPlayer(player1)
    diceGame.addPlayer(player2)
    diceGame.addPlayer(player3)

    for (i in 0 until 5){
        diceGame.play()
        println()
    }
}