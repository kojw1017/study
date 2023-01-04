package 스트링기파이
fun take(n: Int, intArr: MutableList<Int>) = intArr.reversed()

fun main() {
    println(take(26, mutableListOf(1, 4, 3)))
}


interface Json{ val v:Any }
class JsonString(override val v: String):Json
class JsonInt(override val v: Int):Json
class JsonBoolean(override val v: Boolean):Json
class JsonObject:Json{ override val v: MutableMap<String, Json> = mutableMapOf() }
class JsonArray:Json{ override val v: MutableList<Json> = mutableListOf() }

fun typeCheck(json:Json):Pair<List<*>, Boolean>{
    return when(json){
        is JsonArray -> json.v to true
        is JsonObject -> json.v.entries.toList() to false
        else ->{
            throw Throwable("err")
        }
    }
}

fun stringify(json:Json):String{
    return when(json){
        is JsonString -> "\"${json.v}\""
        is JsonInt, is JsonBoolean -> "${json.v}"
        else -> {
            val (list, isArray) = typeCheck(json)
            if(isArray){
                recursive(list, isArray, 0, "[", null)
            }else{
                recursive(list, isArray, 0, "{", null)
            }
        }
    }
}

fun keyNullCheck(key:String): String {
    return if(key.isEmpty()) "" else "\"${key}\":"
}

class Prev(val list: List<*>, val isArray: Boolean, val i: Int, val prev: Prev?)
tailrec fun recursive(list: List<*>, isArray: Boolean, i:Int, acc:String, prev:Prev?):String{
    return if(isArray){
        if(i < list.size){
            when(val a = list[i] as Json){
                is JsonArray ->  recursive(a.v, true, 0, "$acc[", Prev(list, isArray, i+1, prev))
                is JsonObject ->  recursive(a.v.entries.toList(), false, 0, "$acc{", Prev(list, isArray,i+1, prev))
                else -> recursive(list, isArray, i+1, acc+when(a){
                    is JsonString -> "\"${a.v}\","
                    is JsonInt, is JsonBoolean -> "${a.v},"
                    else -> throw Throwable("err")
                },prev)
            }
        }else{
            val result = acc.substring(0, acc.length-1) + "]"
            if(prev == null) result
            else recursive(prev.list, prev.isArray, prev.i, "$result,", prev.prev)
        }
    }else{
        if(i < list.size){
            val json = list[i] as MutableMap.MutableEntry<*,*>
            when(val a = json.value as Json){
                is JsonArray -> recursive(a.v, true, 0, acc+keyNullCheck(json.key.toString())+"[", Prev(list,isArray,i+1,prev))
                is JsonObject -> recursive(a.v.entries.toList(), false, 0, acc+keyNullCheck(json.key.toString())+"{",  Prev(list,isArray,i+1,prev))
                else -> recursive(list, isArray, i+1, acc+when(a){
                    is JsonString -> "\"${a.v}\","
                    is JsonInt, is JsonBoolean -> "\"${json.key}\":${a.v},"
                    else -> throw Throwable("err")
                },prev)
            }
        }else{
            val result = acc.substring(0, acc.length-1) +"}"
            if(prev ==null) result
            else recursive(prev.list, prev.isArray, prev.i, "${result},", prev.prev)
        }
    }
}