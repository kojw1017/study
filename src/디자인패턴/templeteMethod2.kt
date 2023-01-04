package 디자인패턴
/*
    템플릿 메소드 패턴
    어떤 일을 단계별로 처리해야 하지만
    각 단계별로 상황에 따라 다르게 처리하고 싶을때
 */
class Article(private var title: String, private var content: List<String>, private var footer: String ){
    fun getTitle() = title
    fun getContent() = content
    fun getFooter() = footer
}
abstract class DisplayArticleTemplate(protected var article: Article){
    fun display(){
        title()
        content()
        println()
        footer()
    }
    abstract fun title()
    abstract fun content()
    abstract fun footer()
}
class SimpleDisplayArticle(article: Article): DisplayArticleTemplate(article){
    override fun title() = println(article.getTitle())
    override fun content() = article.getContent().forEach(::print)
    override fun footer() = println(article.getFooter())
}
class CaptionDisplayArticle(article: Article): DisplayArticleTemplate(article){
    override fun title() = println("Title : ${article.getTitle()}")
    override fun content() = article.getContent().forEach{ print("$it   ") }
    override fun footer() = println("Footer : ${article.getFooter()}")
}
fun main(){
    val title = "디자인패턴"
    val content = listOf("A","A","A","A","A","A","A")
    val footer = "2002. 10. 21"
    SimpleDisplayArticle(Article(title, content, footer)).display()
    println()
    println()
    CaptionDisplayArticle(Article(title, content, footer)).display()
    val a = "temp3798200315"
    val b = a.split("3798200315")
    println(a.contains("temp${3798200315}"))
    println("bbb-> $b")
}
