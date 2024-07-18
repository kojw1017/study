package 코틀린디자인패턴.생성패턴1

class Mail @PublishedApi internal constructor(){
    companion object{
        inline operator fun invoke(block:Set.()->Unit):Get{
            val mail = Mail()
            Set(mail).block()
            return Get(mail)
        }
    }
    var to:ArrayList<String>? = null
    var title:String = ""
    var content:String = ""
    var files:ArrayList<String>? = null
}

@JvmInline value class Set(@PublishedApi internal val mail:Mail){
    inline fun to(to:String, vararg others:String) =
        (mail.to ?: arrayListOf<String>()
            .also { mail.to = it })
            .run {
                add(to)
                addAll(others)
    }
    inline fun title(title:String){
        if(title.isBlank()) throw  IllegalArgumentException("제목이 비어있습니다.")
        mail.title = title
    }
    inline fun content(content:String){
        if(content.isBlank()) throw  IllegalArgumentException("내용이 비어있습니다.")
        mail.content = content
    }
    inline fun file(file:String, vararg others:String){
        (mail.files ?: arrayListOf<String>()
            .also { mail.files = it })
            .run{
                add(file)
                addAll(others)
            }
    }
    inline fun s3(bucket:String, key:String) = file("s3://$bucket/$key")
    inline fun googleDrive(fileId:String) = file("googleDrive://$fileId")
}

@JvmInline value class Get(@PublishedApi internal val mail:Mail){
    inline val to get() = mail.to ?: listOf()
    inline val title get() = mail.title
    inline val content get() = mail.content
    inline val files get() = mail.files ?: listOf()
}

fun main() {
    val mail = Mail {
        to("")
        title("제목")
        content("내용")
        file("첨부파일1", "첨부파일2")
        s3("bucket", "key")
        googleDrive("fileId")
    }
}