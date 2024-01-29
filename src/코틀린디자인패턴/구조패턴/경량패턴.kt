package 코틀린디자인패턴.구조패턴

// 공유될 수 있는 상태를 가지는 클래스
data class BulletFlyweight(val shape: String, val color: String, val size: Int) {
    fun render(x: Int, y: Int, velocity: Int) {
        // 총알 렌더링 로직
        println("Rendering a $color bullet of shape $shape and size $size at x:$x y:$y with velocity $velocity")
    }
}
// 플라이웨이트 팩토리 클래스
class BulletFactory {
    private val flyweights = hashMapOf<String, BulletFlyweight>()

    fun getFlyweight(shape: String, color: String, size: Int): BulletFlyweight {
        val key = "$shape-$color-$size"
        return flyweights.getOrPut(key) { BulletFlyweight(shape, color, size) }
    }
}
fun main() {
    val factory = BulletFactory()

    // 총알의 외부 상태
    val bullets = mutableListOf<Bullet>()

    // 총알 생성
    for (i in 1..5) {
        val bullet = Bullet(factory.getFlyweight("circle", "red", 2), x = i * 10, y = 0, velocity = i * 5)
        bullets.add(bullet)
    }

    // 총알 렌더링
    bullets.forEach { it.render() }
}

// 총알 객체를 나타내는 클래스
data class Bullet(val flyweight: BulletFlyweight, var x: Int, var y: Int, var velocity: Int) {
    fun render() {
        flyweight.render(x, y, velocity)
    }
}
