/* 추상 팩토리
   추상 컴포넌트와 추상 컴포넌트를 반환하는 추상팩토리를 만들고
   구현부에서 똑같이 구현 컴포넌트와 이를 반환하는 구현 팩토리를 만든다

   확장할때 구현 팩토리와 구현컴포넌트를 만들어쓰면된다 - 확장에는 열려있고 수정에는 닫혀있다
*/

//추상 팩토리 - 추상클래스나 인터페이스로 정의가능 추상 팩토리의 메서드의 인자는 추상 컴포넌트의 생성자 인자와 같다
abstract class ComponentFactory{
    abstract fun createButton(caption: String): Button
    abstract fun createCheckBox(bChecked: Boolean): CheckBox
    abstract fun createTextEdit(value: String): TextEdit
}
//추상 컴포넌트
abstract class Button(protected var caption: String){
    fun clickEvent() = println("$caption 버튼을 클릭했습니다")
    abstract fun render()
}
abstract class CheckBox(protected var bChecked: Boolean){
    fun setChecked(checked: Boolean) = run {
        bChecked = checked
        render()
    }
    abstract fun render()
}
abstract class TextEdit(protected  var value: String){
    fun setV(v: String) = run {
        value = v
        render()
    }
    abstract fun render()
}
//구현 팩토리
class MacOsFactory: ComponentFactory(){
    override fun createButton(caption: String) = MacOsButton(caption)
    override fun createCheckBox(bChecked: Boolean) = MacOsCheckBox(bChecked)
    override fun createTextEdit(value: String) = MacOsTextEdit(value)
}
class WindowsFactory: ComponentFactory(){
    override fun createButton(caption: String) = WindowsButton(caption)
    override fun createCheckBox(bChecked: Boolean) = WindowsCheckBox(bChecked)
    override fun createTextEdit(value: String) = WindowsTextEdit(value)
}
class LinuxFactory: ComponentFactory(){
    override fun createButton(caption: String) = LinuxButton(caption)
    override fun createCheckBox(bChecked: Boolean) = LinuxCheckBox(bChecked)
    override fun createTextEdit(value: String) = LinuxTextEdit(value)
}
//구현 컴포넌트
class WindowsButton(caption: String): Button(caption){
    override fun render() =
        println("Windows Api를 통해 $caption 버튼을 그립니다")
}
class LinuxButton(caption: String): Button(caption){
    override fun render() =
        println("Linux Api를 통해 $caption 버튼을 그립니다")
}
class WindowsCheckBox(bChecked: Boolean): CheckBox(bChecked){
    override fun render() =
        println("Windows Api를 통해 ${if(bChecked) "체크된" else "체크 안된"} 체크박스를 그립니다")
}
class LinuxCheckBox(bChecked: Boolean): CheckBox(bChecked){
    override fun render() =
        println("Linux Api를 통해 ${if(bChecked) "체크된" else "체크 안된"} 체크박스를 그립니다")
}
class WindowsTextEdit(value: String): TextEdit(value){
    override fun render() =
        println("Windows Api를 통해 $value 값을 가진 텍스트에디터를 그립니다")
}
class LinuxTextEdit(value: String): TextEdit(value){
    override fun render() =
        println("Linux Api를 통해 $value 값을 가진 텍스트에디터를 그립니다")
}
class MacOsButton(caption: String): Button(caption){
    override fun render() =
        println("MacOs Api를 통해 $caption 버튼을 그립니다")
}
class MacOsCheckBox(bChecked: Boolean): CheckBox(bChecked){
    override fun render()=
        println("MacOs Api를 통해 ${if(bChecked) "체크된" else "체크 안된"} 체크박스를 그립니다")
}
class MacOsTextEdit(value: String): TextEdit(value){
    override fun render() =
        println("MacOs Api를 통해 $value 값을 가진 텍스트에디터를 그립니다")
}
fun main(){
    val factory = WindowsFactory()
    val factor2 = LinuxFactory()
    val factor3 = MacOsFactory()

    factory.createButton("확인").render()
    factory.createCheckBox(false).render()
    factory.createTextEdit("디자인 패턴").render()

    factor2.createButton("확인").render()
    factor2.createCheckBox(false).render()
    factor2.createTextEdit("디자인 패턴").render()

    factor3.createButton("확인").render()
    factor3.createCheckBox(false).render()
    factor3.createTextEdit("디자인 패턴").render()
}
