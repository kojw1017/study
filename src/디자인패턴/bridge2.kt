package design1

/*
    브리지 패턴
    기능 계층과 구현계층으로 나뉜다
    기능 계층과 구현계층을 연결해 주는게 브리지 패턴
 */

//구현 계층 - 데이터를 다양한 형태로 표현
interface Display{
    fun title(draft: Draft)
    fun author(draft: Draft)
    fun content(draft: Draft)
}

class SimpleDisplay: Display{
    override fun title(draft: Draft) = println(draft.getTitle())
    override fun author(draft: Draft) = println(draft.getAuthor())
    override fun content(draft: Draft) = draft.getContent().forEach(::println)
}

class CaptionDisplay: Display{
    override fun title(draft: Draft) = println("제목 : ${draft.getTitle()}")
    override fun author(draft: Draft) = println("저자 : ${draft.getAuthor()}")
    override fun content(draft: Draft) {
        print("내용 : ")
        draft.getContent().forEach(::println)
    }
}

//기능 계층 - 새로운 기능이 추가되어도 기존 코드 수정할 필요없이 확장에 열려있다
open class Draft(
    private var title: String,
    private var authro: String,
    private var content: MutableList<String>)
{
    fun getTitle() = title
    fun getAuthor() = authro
    fun getContent() = content

    open fun print(display: Display){
        display.title(this)
        display.author(this)
        display.content(this)
    }
}

class Publication(
    title: String,
    author: String,
    content: MutableList<String>,
    private var publisher: String,
    private var cost: Int
): Draft(title, author, content){
    private fun printPublicationInfo() = println("#$publisher $$cost")
    override fun print(display: Display) {
        super.print(display)
        printPublicationInfo()
        println()
    }
}


fun main(){
    val title = "복원된 지구"
    val author = "김형준"
    val content = mutableListOf("플라스틱 사용의 감소와",
        "바다 생물 어획량 감소로 인하여",
        "지구는 복원되었다")
    val publisher = "북악 출판"
    val cost = 100
    val draft = Draft(title, author, content)
    val publication = Publication(title, author, content, publisher, cost)

//    draft.print(SipmpleDisplay())
//    draft.print(CaptionDisplay())

    publication.print(SimpleDisplay())
    publication.print(CaptionDisplay())
}