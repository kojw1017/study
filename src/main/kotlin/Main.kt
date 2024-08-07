package org.example


// 메인 함수에서 MemberManagementSystem 사용 예시
fun main() {
    listOf(2, 4, 0, 8).flatMap { if(it != 0) listOf(16/it) else listOf() }.forEach {
        println(it)
    }
}
