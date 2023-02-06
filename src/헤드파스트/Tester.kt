package 헤드파스트

fun testType(unit: Unit, type: String, expected: String){
    println("Test Setting")
    unit.type = type
    var outputType = unit.type
    if(expected == outputType) println("Test Pass")
    else println("Test Fail $outputType didns match $expected")
}

fun testUnitSpecificProperty(unit: Unit, propertyName: String, inputValue: Any, expected: String){
    println("Test Setting")
    val outputValue = unit.getProperty(propertyName)
    if(expected == outputValue) println("Test Pass")
    else println("Test Fail $outputValue didns match $expected")
}

fun testChangeProperty(unit: Unit, propertyName: String, inputValue: Any, expected: String){
    println("Test Setting")
    unit.setProperty(propertyName, inputValue)
    val outputValue = unit.getProperty(propertyName)
    if(expected == outputValue) println("Test Pass")
    else println("Test Fail $outputValue didns match $expected")
}

fun testChangeProperty(unit: Unit, propertyName: String){
    println("Test Setting")
    val outputValue = unit.getProperty(propertyName)
    if(outputValue == null) println("Test Pass")
    else println("Test Fail $outputValue")
}
fun main() {

}