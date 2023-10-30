package 코틀린디자인패턴
interface ChessPiece{
    val file:Char
    val rank:Char
}
data class Pawn(
    override val file:Char,
    override val rank: Char
):ChessPiece
data class Queen(
    override val file:Char,
    override val rank: Char
):ChessPiece
fun createPiece(notation:String):ChessPiece{
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