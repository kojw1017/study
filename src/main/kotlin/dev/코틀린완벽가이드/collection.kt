package 코틀린완벽가이드
fun main() {
    println(listOf("a", "x", "c" ,"d", "c").reduceIndexed { index, acc, s ->
        println(index)
        acc + s
    })
    val map = mapOf("a" to 1, "b" to 2)
    map.forEach{
        it as MutableMap.MutableEntry
        it.setValue(3)
    }
    println(map)
}
class Child {
//내부에서는 뮤터블 외부에서는 이뮤터블 뮤터블 -> 이뷰터블 은 업캐스팅이라 안전하다
//    private val _map = mutableMapOf("" to "")
//    val map = _map as Map<*, *>
}
/*
 1. 코틀린 컬렉션 타입의 개요, 자바와 코틀린의 가장 중요한 차이
    그냥화살표는 인터헤이스
    mutableMapOf()는 링크드해시맵이다 -> 부하가심해진다
    hashMapOf()를 쓰자
    map을 foreach돌면 entry가나온다
    map은 딕셔너리로쓰려구 했지 for문돌려고 스는게 아니다


 2. 컬력션 타입이 제공하는 기본 연산
    모든 컬렉션은 iterator함수를 지원하고 코틀린에서는 더 간결한 방법으로 사용할 수있다
    원소 개수를 돌려주는 size
    원소가 있는지 없는지 불린으로 반환해주는 isEmpty
    지정한 원소나 모든 원소가 수신객체에 있는지 검사하는 contains containsAll
    add remove addAll removeAll retainAll clear
    += -= 복합연산자 사용가능 가변 변수는 쓸 수 없다
    불변이든 가변이든 + -연산자 지원 원본을 두고 새로운 컬렉션 생성

    subList금지 왜냐면 원본이 바뀜 slice로 사본을 얻는게 좋다

    코틀린 null에 대해서
    null은 타입을 무력화시킨다 존나쌔다....
    코틀린은 타입을 지정할때 null을 허용하지 않을 수 있기 때문에 안전하다
    코틀린은 null을 특별한 의미로 사용 할 수있다
    타입안정성이 보장 된다면 예외대신 null 사용가능
    코틀린 컬렉션에는 orNull 시리즈가 많은대 이유는 예외대신 null을 사용해라 라는 뜻이 있다
    null로 대체할경우 runtime흐름을 부드럽게하고 예외처리는 큰 부하가 걸린다
    코틀린개발을 잘한댜? try catch가 별로 없다
    널을 의식적으로 사용한다

 3. 컬렉션 원소를 이터레이션하는 여러가지 방법
    while do-while for(범위 사용) .. downTo step until
    map은 엔트리 반환

 4. 컬렉션 원소에 접근할 때 쓸 수 있는 공통 함수 나열후 설명
    first last
    firstOrNull lastOrNull
    중괄호 안에넣으면 조건에맞는 원소 반환
    single() 싱글턴 컬렉션 원소반환
    인덱스를 사용한 elementAt과 elementAtOrNull

 5. 코틀린 라이브러리가 제공하는 공통 집계 함수
    조건에 맞는 원소 개수를 반환하는 count sum 원소타입을 수로 변환후 합계구할땐 sumOf
    average 평균 항상 더블 반환 최소값 최대값구하는 minOrNull maxOrNull
    minByOrNull maxByOrNull 중괄호 안에넣으면 조건에맞는 원소 반환
    원소를 문자열로 엮는 joinToString()
    문자열을 새로 생성하지 않고 파라미터로 받은 Appendable 객체뒤에 붙여준다

    집계합수는 fold와 reduce의 편의 함수
    시퀀스의 집계함수는 전혀 다르게 일어난다 터미널이 되기 때문에 조심해서 써야한다


 6. fold 와 reduce 설명
    reduce 첫째 누적된값 둘째 컬렉션 현재값
    누적값은 컬렉션의 첫번째 요소고, 반환 값은 컬렉션의 자료형

    누적값 반환
    누적값을 첫번째 원소로 정해놓고 다음 원소부터 연산을 시작하기 때문입니다.

    fold는 누적값을 다른 타입의 값으로 만들 수 있음
    누적값은 파라미터를 통해 자유롭게 정할 수 있으며
    마지막 원소부터 반대 방향으로 계산 수행할때는 함수 이름뒤 Right붙이면됨

    첫번째 요소도 연산에 포함하고 싶으면 fold를쓰면된다

 7. all any none 함수의 목적
    컬렉션이 조건을 만족하는지 검사할때 쓰는 함수
    all 다만족 any 일부만족 none 하나도 만족하지 않을때
    브레이크 해주는 메소드로 이 세개밖에 없다
    드모르간 법칙 이게 뭐였더라...
    컴퓨터 사이언스는 명제논리로 시작해서 수로 논리로 확장한것

 8. 컬렉션의 걸러내기 함수
    조건에맞는것만 다시 컬렉션으로 만들어주는 filter
    맵의 경우 filterKey나 filterValues

    조건을 부정하는거만 고르는 filterNot
    인덱스와같이 사용하는 filterIndexed
    null을 걸러주는 filterNotNull
    특정 타입만 남기는 filterIsInstance

    걸러낸 결과를 가변 컬렉션에 넣고싶으면 끝에 To를붙이자자

    partition은 조건을걸어 true false인지에 따라 Pair로 객체를 반환해 준다
 9 하위컬렉션을 추출하는 방법
    slice() 지정한 인덱스에 속하는 원소로 새로운 리스트 만든다
    다른 배열로 추출하고 싶다면 sliceArray사용

    개수만큼 추출하는 take takeLast
    주어진 개수 빼고 추출하는 drop dropLast

    조건에 맞는거만 추출하는 while

    chunked()는 배열의 원소들을 파라미터로 넘어온 수로 묶어 준다
    청크는 소비를하고
    윈도우드는 여러번소비

    slice는 원본을 바꾼다 sliceArray를 쓰자  원본을 바꾸는건 유지보수하기 굉장히 힘들 수 있다
    함정이라 본다 subList 와 slice

 10.컬렉션에 적용할 수 있는 표준 변환 함수들 설명 매핑(map), 평평하게 하기(flatten), 연관 짓기(associateBy) 설명
    map은 수식을 적용하면 flatten각 컬렉션을 하나의 컬렉션으로 합쳐준다 그값을 컬렉션으로만들어준다

    associateBy 키를 추출하여 map으로 변환
    헷갈리면 그냥 associate 쓰도 된다

 11.코틀린 표준 라이브러리가 제공하는 컬렉션 순서 지정 및 정렬 유틸리티 설명

 12.스트림 생성과 변환 유틸리티 설명

 13.파일이나 I/O 스트림의 콘텐츠에 접근하기 위해 사용할 수있는 함수를 나열하고 설명하라

 14.파일 시스템 유틸리티 함수 설명
 cpu와 메모리 반비례
 메모리 크기는 컴퓨터에 따라다 다르다
 메모리풀로 죽는거에대한 익셉션
 */