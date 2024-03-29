package 코틀린디자인패턴.생성패턴

/**
    빌더 패턴


 */

data class Mail_V1(
    val to: List<String>,
    val cc: List<String>?,
    val title: String?,
    val message: String?,
    val important: Boolean,
)

data class Mail_V2(
    val to: List<String>,
    private var _cc: List<String>? = null,
    private var _title: String? = null,
    private var _message: String? = null,
    private var _important: Boolean? = null,
){
    fun cc(cc: List<String>) = apply {
        _cc = cc
    }
    fun title(title: String) = apply {
        _title = title
    }
    fun message(message: String) = apply {
        _message = message
    }
    fun important(important: Boolean) = apply {
        _important = important
    }
}
class Mail internal constructor(
    val to: List<String>,
    val cc: List<String>?,
    val title: String?,
    val message: String?,
    val important: Boolean,
)
class MailBuilder{
    private var to: List<String> = listOf()
    private var cc: List<String> = listOf()
    private var title: String = ""
    private var message: String = ""
    private var important: Boolean = false

    fun build(): Mail {
        if(to.isEmpty()) throw RuntimeException("To 속성 없음")
        return Mail(to, cc, title, message, important)
    }
    fun to(to: List<String>): MailBuilder {
        this.to = to
        return this
    }
    fun cc(cc: List<String>): MailBuilder {
        this.cc = cc
        return this
    }
    fun title(title: String): MailBuilder {
        this.title = title
        return this
    }
    fun message(message: String): MailBuilder {
        this.message = message
        return this
    }
    fun important(important: Boolean): MailBuilder {
        this.important = important
        return this
    }
}

class MailBuilder2{
    private var to: List<String> = listOf()
    private var cc: List<String>? = null
    private var title: String? = null
    private var message: String? = null
    private var important: Boolean = false

    fun build(): Mail {
        require(to.isNotEmpty()) { "To 속성 없음" }
        return Mail(to, cc, title, message, important)
    }
    fun to(to: List<String>) = apply { this.to = to }
    fun cc(cc: List<String>) = apply { this.cc = cc }
    fun title(title: String) = apply { this.title = title }
    fun message(message: String) = apply { this.message = message }
    fun important(important: Boolean) = apply { this.important = important }
}

fun main() {
  val mail = MailBuilder().to(listOf("")).build()
}