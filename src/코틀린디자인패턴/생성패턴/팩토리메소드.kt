package 코틀린디자인패턴.생성패턴

/**
    팩토리 메서드 - 객체를 생성하는 메서드

    필요로 하는 객체의 정확한 타입과 종속성을 알 수 없거나,
    객체 생성 중에 특정 프로세스를 적용해야하는 경우에 유용

    이 패턴을 사용하면 개발자가 프로그램 코드(main)와 객체 생성 코드(createPiece) 사이의 결합도를 줄일 수 있다는 것입니다.
    이로써 코드의 유연성과 재사용성이 향상되며,
    나중에 쉽게 다른 종류의 객체를 추가할 수 있게 됩니다.
 */
interface ChessPiece{
    val file:Char
    val rank:Char
}
data class Pawn(
    override val file:Char,
    override val rank: Char
): ChessPiece
data class Queen(
    override val file:Char,
    override val rank: Char
): ChessPiece
fun createPiece(notation:String): ChessPiece {
    val (type, file, rank) = notation.toCharArray()
    return when(type){
        'q' -> Queen(file, rank)
        'p' -> Pawn(file, rank)
        else -> throw RuntimeException("알수 없는 종류:$type")
    }
}
fun main(){
    val notation = listOf("pa8", "qc3")
    val pieces = mutableListOf<ChessPiece>()
    for(n in notation){
        pieces.add(createPiece(n))
    }
    println(pieces)
}