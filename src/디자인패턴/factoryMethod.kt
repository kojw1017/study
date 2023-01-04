package 디자인패턴.a

import java.io.FileWriter
import java.io.IOException

abstract class Item(protected var caption: String){
    abstract fun makeHTML(): String
}
abstract class Link(protected var url: String, caption: String): Item(caption)
abstract class Tray(caption: String): Item(caption){
    protected var tray = mutableListOf<Item>()
    fun add(item: Item){ tray.add(item) }
}

abstract class Page(protected var title: String, protected var author: String){
    protected var content = mutableListOf<Item>()
    fun add(item: Item){ content.add(item) }
    fun output(){
        try {
            val fileName = "$title.html"
            val writer = FileWriter(fileName)
            writer.write(makeHTML())
            writer.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
    abstract fun makeHTML(): String
}

abstract class Factory{
    companion object{
        fun getFactory(className: String):Factory {
            var factory: Factory = null!!
            try {
                factory = Class.forName(className).newInstance() as Factory
            }catch (e: ClassNotFoundException){
                println("$className 이런 클래스 없습니다")
                e.printStackTrace()
            }catch (e: Exception){
                e.printStackTrace()
            }
            return factory
        }
    }
    abstract fun createLink(caption: String, url: String): Link
    abstract fun createTray(caption: String): Tray
    abstract fun createPage(title: String, author: String): Page
}

class ListFactory: Factory(){
    override fun createLink(caption: String, url: String) = ListLink(caption, url)
    override fun createTray(caption: String) = ListTray(caption)
    override fun createPage(title: String, author: String) = ListPage(title, author)
}

class ListLink(caption: String, url: String): Link(caption, url){
    override fun makeHTML() = " <li><a href=\"$url\"> $caption </a></li>\n"
}

class ListTray(caption: String): Tray(caption){
    override fun makeHTML(): String {
        val buffer = StringBuffer()
        buffer.append("<li>\n")
        buffer.append("$caption \n")
        buffer.append("<ul>\n")
        val it = tray.iterator()
        while (it.hasNext()){ buffer.append(it.next().makeHTML()) }
        buffer.append("</ul>\n")
        buffer.append("</li>\n")
        return buffer.toString()
    }
}

class ListPage(title: String, author: String): Page(title, author){
    override fun makeHTML(): String {
        val buffer = StringBuffer()
        buffer.append("<html><head><title> $title </title></head>\n")
        buffer.append("<body>\n")
        buffer.append("<h1> $title </h1>\n")
        buffer.append("<ul>\n")
        val it = content.iterator()
        while (it.hasNext()){ buffer.append(it.next().makeHTML()) }
        buffer.append("</ul>\n")
        buffer.append("<hr><address> $author </address>")
        buffer.append("</body></html>\n")
        return buffer.toString()
    }
}

class TableFactory: Factory(){
    override fun createLink(caption: String, url: String) = TableLink(caption, url)
    override fun createTray(caption: String) = TableTray(caption)
    override fun createPage(title: String, author: String) = TablePage(title, author)
}

class TableLink(caption: String, url: String): Link(caption, url){
    override fun makeHTML() = "<td><a href=\\$url\\> $caption </a></td>\n"
}
class TableTray(caption: String):Tray(caption){
    override fun makeHTML(): String{
        val buffer = StringBuffer()
        buffer.append("<td>")
        buffer.append("<table width='100%' border='1'><tr>")
        buffer.append("<td bgcolor='#cccccc' align='center' colspan='${tray.size}'<b>$caption</b></td>")
        buffer.append("</tr>")
        buffer.append("<tr>")
        val it = tray.iterator()
        while (it.hasNext()){ buffer.append(it.next().makeHTML()) }
        buffer.append("</tr></table>")
        buffer.append("</td>")
        return buffer.toString()
    }
}
class TablePage(title: String, author: String):Page(title, author){
    override fun makeHTML(): String{
        val buffer = StringBuffer()
        buffer.append("<html><head><title> $title </title></head>\n")
        buffer.append("<body>\n")
        buffer.append("<h1> $title </h1>\n")
        buffer.append("<table width='80%' border='3'>\n")
        val it = content.iterator()
        while (it.hasNext()){ buffer.append(it.next().makeHTML()) }
        buffer.append("</table>")
        buffer.append("<hr><address> $author </address")
        buffer.append("</body></html>\n")
        return buffer.toString()
    }
}
fun main(){
    val factory = Factory.getFactory("className")
    val joins = factory.createLink("중앙일보", "http")
    val traynews = factory.createTray("신문")
    traynews.add(joins)
    val page = factory.createPage("LinkPage", "영진닷컴")
    page.add(traynews)
    page.output()
}