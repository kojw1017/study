package 코테22

class Solution {
    //가장많이받은선물
    private fun validateFriendsAndGifts(friends: Array<String>, gifts: Array<String>) {
        validateFriends(friends)
        validateGifts(gifts, friends)
    }
    private fun validateFriends(friends: Array<String>) {
        require(friends.size in 2..50) { "친구 수는 2 이상 50 이하여야 합니다." }
        require(friends.all { it.matches(Regex("^[a-z]*$")) }) { "친구 이름은 알파벳 소문자로만 이루어져야 합니다." }
        require(friends.size == friends.distinct().size) { "중복된 이름이 없어야 합니다." }
    }
    private fun validateGifts(gifts: Array<String>, friends: Array<String>) {
        require(gifts.size in 1..10000) { "선물 수는 1 이상 10000 이하여야 합니다." }

        gifts.forEach { gift ->
            val (giver, receiver) = gift.split(" ").also {
                require(it.size == 2) { "선물 기록은 공백으로 구분된 두 이름으로 이루어져야 합니다." }
            }
            require(giver in friends && receiver in friends) { "선물을 주고받는 사람은 친구 목록에 있어야 합니다." }
            require(giver != receiver) { "선물을 주는 사람과 받는 사람이 같을 수 없습니다." }
        }
    }
    fun solution(friends: Array<String>, gifts: Array<String>): Int {
        //밸리데이션 체크
        validateFriendsAndGifts(friends, gifts)
        // 선물 기록을 저장할 맵
        val giftMap = mutableMapOf<String, MutableMap<String, Int>>()
        // 선물 지수를 저장할 맵
        val giftIndex = mutableMapOf<String, Int>()
        // 다음 달 선물 계산
        val nextMonthGifts = mutableMapOf<String, Int>()

        // 초기화
        friends.forEach { friend ->
            giftMap[friend] = mutableMapOf()
            friends.forEach { other ->
                if (friend != other) giftMap[friend]!![other] = 0
            }
            giftIndex[friend] = 0
            nextMonthGifts[friend] = 0
        }

        // 선물 기록 처리
        gifts.forEach { gift ->
            val (giver, receiver) = gift.split(" ")
            giftMap[giver]!![receiver] = giftMap[giver]!![receiver]!! + 1
            giftIndex[giver] = giftIndex[giver]!! + 1
            giftIndex[receiver] = giftIndex[receiver]!! - 1
        }

        // 다음 달 선물 계산
        for(i in friends.indices){
            for(j in i+1 until friends.size){
                val friends1 = friends[i]
                val friends2 = friends[j]
                val gift1to2 = giftMap[friends1]!![friends2]!!
                val gift2to1 = giftMap[friends2]!![friends1]!!
                when{
                    //두사람 사이 이번달 선물을 더많이 준사람은 다음달에 준사람에게 선물을 하나 받음
                    //두사람 사이 선물을 주고받은 기록이 없거나 주고 받은 수가 같을 경우 선물지수 큰사람이 작은사람에게 선물
                    //선물 지수도 같으면 선물 주고받지 않음
                    gift1to2 > gift2to1 -> nextMonthGifts[friends1] = nextMonthGifts[friends1]!! + 1
                    gift1to2 < gift2to1 -> nextMonthGifts[friends2] = nextMonthGifts[friends2]!! + 1
                    else -> {
                        val index1 = giftIndex[friends1]!!
                        val index2 = giftIndex[friends2]!!
                        when{
                            index1 > index2 -> nextMonthGifts[friends1] = nextMonthGifts[friends1]!! + 1
                            index1 < index2 -> nextMonthGifts[friends2] = nextMonthGifts[friends2]!! + 1
                        }
                    }
                }
            }
        }
        return nextMonthGifts.maxOfOrNull { it.value } ?: 0
    }
}

fun main() {
    val friends = arrayOf("muzi", "ryan", "frodo", "neo")
    val gifts = arrayOf("muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi")
    val s = Solution()
    println(s.solution(friends, gifts))
}
