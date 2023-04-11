package 코어코틀린

//336p


fun main() {
    val words = "This is an example of a sentence".split(" ")
    println(words)
    val charList = words.map{ it.toList() }
    println(charList.flatMap{ it.mapIndexed { i, c ->
        println(i)
        println(c)
    }})
    val firstLetterCapitalizedCharList = charList.flatMap{it.mapIndexed{index, c
        -> if(index==0) c.uppercaseChar() else c}}
}