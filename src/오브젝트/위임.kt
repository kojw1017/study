package 오브젝트

interface Speaker{
    val subject: String
    val script: String
    fun say()
}
class DroidKnights: Speaker{
    override val subject = "jetPack Compose 작동 원리"
    override val script = """
        dawdjwaiudhjawidhawiudhauiw
    """.trimIndent()

    override fun say() {
        println("[$subject] $script")
    }
}
class Sungbin1(private val presentation: Speaker): Speaker{
    override val subject = presentation.subject
    override val script = presentation.script

    override fun say() {
        presentation.say()
    }
}

class Sungbin2(private val presentation: Speaker): Speaker by presentation