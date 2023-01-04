package Adapter

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/*
    플라이웨이트 패턴
    객체를 사용하기 위해 매번 생성하지 않고 한번만 생성하고
    다시 필요할때 이전에 생성된 객체를 재사용 하는 목록

    객체 생성 시 자원이 많이 소모될 경우 이패턴을 사용해 적은 자원으로 필요 객체 사용 가능
*/
class Number(number: Int){
    private var digitList = mutableListOf<Digit>()
    init {
        val strNum = number.toString()
        val len = strNum.length
        val digitFactory = DigitFactory()
        for (i in 0 until len){
            val n = strNum[i].code
            val digit = digitFactory.getDigit(n)
            digit?.let { digitList.add(it) }
        }
    }
    fun print(x: Int, y: Int){
        for (i in 0 until digitList.size){
            val digit = digitList[i]
            digit.print(x+(i*8), y)
        }
    }
}

class DigitFactory{
    private var pool = arrayOfNulls<Digit>(10)
    fun getDigit(n: Int): Digit? {
        return if(pool[n] != null){
            pool[n]
        }else{
            val fileName = String.format("./data/%d.txt", n)
            val digit = Digit(fileName)
            pool[n] = digit
            digit
        }
    }
}
class Digit(var fileName: String){
    private var data = mutableListOf<String>()
    init {
        var fr: FileReader? = null
        var br: BufferedReader? = null
        try {
            fr = FileReader(fileName)
            br = BufferedReader(fr)
            for (i in 0 until 8){
                data.add(br.readLine())
            }
        } catch (e: IOException){
            e.printStackTrace()
        } finally {
            try {
                fr?.close()
                br?.close()
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
    fun print(x: Int, y: Int){
        for (i in 0 until 8){
            val line = data[i]
            print(String.format("%c[%d,%df", 0x1b, y+i, x))
            print(line)
        }
    }
}

fun main(){

}