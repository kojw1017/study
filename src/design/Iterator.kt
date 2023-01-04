package design


interface Aggregate {
    fun iterator(): Iterator
}

interface Iterator {
    fun hasNext():Boolean
    fun next():Any
}

class Book(var name:String)
class BookShelf(private var books: MutableList<Book>, private var last:Int = 0): Aggregate{
    fun getBookAt(index: Int) = books[index]
    fun appendBook(book: Book) {
        books += book
        last++
    }
    fun getLength() = last
    override fun iterator() = BookShelfIterator(this)
}

class BookShelfIterator(private var bookShelf: BookShelf, private var index: Int = 0): Iterator {
    override fun hasNext() = index < bookShelf.getLength()
    override fun next(): Any {
        val book = bookShelf.getBookAt(index)
        index++
        return book
    }
}

fun main(){
    val bookShelf = BookShelf(mutableListOf())
    bookShelf.appendBook(Book("a"))
    bookShelf.appendBook(Book("b"))
    bookShelf.appendBook(Book("c"))
    bookShelf.appendBook(Book("d"))
    val it = bookShelf.iterator()
    while (it.hasNext()){
        val book: Book = it.next() as Book
        println(book.name)
    }
}
