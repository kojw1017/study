package 코테22

class Solution {
    fun solution(friends: Array<String>, gifts: Array<String>): Int {
        var answer: Int = 0
        val a = friends.associateWith { name ->
            gifts.count{ name == it.split(" ")[0] } - gifts.count{ name == it.split(" ")[1] }
        }
        a.forEach {
            println("k->${it.key} v->${it.value}")
        }
        return answer
    }
}


//fun main(){
//    Solution().solution(arrayOf("muzi", "ryan", "frodo", "neo"), arrayOf("muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"))
//}

data class User(val id: Int, val name: String, val age: Int, val city: String, val email: String)

fun main() {
    val users = listOf(
        User(1, "Alice", 30, "London", "alice@example1.com"),
        User(2, "Bob", 28, "New York", "bob@example1.com"),
        User(3, "Carol", 32, "San Francisco", "carol@example1.com"),
        User(4, "Dave", 39, "London", "dave@example2.com"),
        User(5, "Eve", 30, "Paris", "eve@example2.com"),
        User(6, "Frank", 25, "Berlin", "frank@example2.com")
    )

    // 나이가 30 이상인 사람들의 이름 목록 만들기
    val namesOfOlderThan30 = users.filter { it.age >= 30 }.map { it.name }
    println("Names of Older Than 30: $namesOfOlderThan30")
    // 결과: [Alice, Carol, Dave, Eve]

    // reduce: 모든 사람들의 나이 합계
    val totalAge = users.map { it.age }.reduce { sum, age -> sum + age }
    println("Total Age: $totalAge")
    // 결과: 184

    // flatMap: 각 이름의 문자를 리스트로 변환 후 평탄화
    val characters = users.map { it.name }.flatMap { it.toList() }
    println("Characters: $characters")
    // 결과: [A, l, i, c, e, B, o, b, C, a, r, o, l, D, a, v, e, E, v, e, F, r, a, n, k]

    // partition: 나이가 30 이상인 사람과 그렇지 않은 사람으로 나누기
    val (older, younger) = users.partition { it.age >= 30 }
    println("Older and Younger: $older, $younger")
    // 결과: ([User(id=1, name=Alice, age=30, city=London), User(id=3, name=Carol, age=32, city=San Francisco), User(id=4, name=Dave, age=39, city=London), User(id=5, name=Eve, age=30, city=Paris)], [User(id=2, name=Bob, age=28, city=New York), User(id=6, name=Frank, age=25, city=Berlin)])

    // zip: 이름과 도시를 쌍으로 묶기
    val nameAndCity = users.map { it.name }.zip(users.map { it.city })
    println("Name and City: $nameAndCity")
    // 결과: [(Alice, London), (Bob, New York), (Carol, San Francisco), (Dave, London), (Eve, Paris), (Frank, Berlin)]

    // associate: 이름을 키로, 나이를 값으로 하는 맵 만들기
    val nameToAge = users.associate { it.name to it.age }
    println("Name to Age: $nameToAge")
    // 결과: {Alice=30, Bob=28, Carol=32, Dave=39, Eve=30, Frank=25}

    // 30세 이상의 사용자를 찾아 그들의 이름을 대문자로 변환하고 도시별로 그룹화하기
    val groupedByCity = users.filter { it.age >= 30 }
        .map { it.copy(name = it.name.uppercase()) }
        .groupBy { it.city }
    println("Grouped by City: $groupedByCity")
    // 결과: {London=[User(id=1, name=ALICE, ...), User(id=4, name=DAVE, ...)], San Francisco=[User(id=3, name=CAROL, ...)], Paris=[User(id=5, name=EVE, ...)]}

    // 모든 사용자의 이메일 도메인을 추출하고 중복을 제거한 후 정렬하기
    val emailDomains = users.map { it.email.substringAfter('@') }
        .distinct()
        .sorted()
    println("Email Domains: $emailDomains")
    // 결과: [example1.com, example2.com]

    // 사용자 이름의 첫 글자별로 그룹화하고, 각 그룹의 평균 나이 계산하기
    val averageAgeByNameInitial = users.groupBy { it.name.first() }
        .mapValues { (_, users) -> users.map { it.age }.average() }
    println("Average Age by Name Initial: $averageAgeByNameInitial")
    // 결과: {A=30.0, B=28.0, C=32.0, D=39.0, E=30.0, F=25.0}

    // 사용자를 나이에 따라 정렬하고, 각 사용자의 이름과 나이를 쌍으로 만든 리스트 생성하기
    val namesAndAges = users.sortedBy { it.age }
        .map { it.name to it.age }
    println("Names and Ages: $namesAndAges")
    // 결과: [(Frank, 25), (Bob, 28), (Alice, 30), (Eve, 30), (Carol, 32), (Dave, 39)]

    // 도시별 평균 나이 계산하기
    val averageAgeByCity = users.groupBy { it.city }
        .mapValues { (_, users) -> users.map { it.age }.average() }
    println("Average Age by City: $averageAgeByCity")
    // 결과: {London=34.5, New York=28.0, San Francisco=32.0, Paris=30.0, Berlin=25.0}

    // 각 도시에서 가장 젊은 사용자 찾기
    val youngestByCity = users.groupBy { it.city }
        .mapValues { (_, users) -> users.minByOrNull { it.age } }
    println("Youngest by City: $youngestByCity")
    // 결과: {London=User(id=1, name=Alice, ...), New York=User(id=2, name=Bob, ...), San Francisco=User(id=3, name=Carol, ...), Paris=User(id=5, name=Eve, ...), Berlin=User(id=6, name=Frank, ...)}
}
