package 코틀린디자인패턴.생성패턴21333
/**
 * 새로운 객체를 생성할 때 기존 객체를 복제하여 생성.
 * 이를 통해 특히 객체 생성 비용이 높거나, 객체의 초기화가 복잡할 경우 효율적으로 객체를 생성
 */
interface Prototype {
    fun clone(): Prototype
}
class Enemy(val health: Int, val damage: Int, var position: Position) : Prototype {
    data class Position(var x: Int, var y: Int)

    // 위치 정보를 변경하면서 적 캐릭터 복제
    override fun clone(): Prototype {
        return Enemy(health, damage, position.copy()).apply {
            // 복제된 적의 위치를 변경
            position.x += 100
            position.y += 100
        }
    }
}

fun main() {
    val originalEnemy = Enemy(100, 15, Enemy.Position(0, 0))
    val clonedEnemy = originalEnemy.clone() as Enemy

    println("Original Enemy Position: ${originalEnemy.position.x}, ${originalEnemy.position.y}")
    println("Cloned Enemy Position: ${clonedEnemy.position.x}, ${clonedEnemy.position.y}")
}


