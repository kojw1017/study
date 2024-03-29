package 코틀린디자인패턴.구조패턴

// Component
interface FileSystemItem {
    fun print(structure: String = "")
}

// Leaf
class File(private val name: String) : FileSystemItem {
    override fun print(structure: String) {
        println("$structure$name")
    }
}

// Composite
class Directory(private val name: String) : FileSystemItem {
    private val children = mutableListOf<FileSystemItem>()

    fun add(item: FileSystemItem) = children.add(item)

    override fun print(structure: String) {
        println("$structure$name")
        children.forEach { it.print(structure + "-") }
    }
}

// 클라이언트 코드
fun main() {
    val file1 = File("File1.txt")
    val file2 = File("File2.txt")
    val dir1 = Directory("dir1")
    dir1.add(file1)
    dir1.add(file2)

    val file3 = File("File3.txt")
    val mainDir = Directory("main")
    mainDir.add(dir1)
    mainDir.add(file3)

    mainDir.print()
}
