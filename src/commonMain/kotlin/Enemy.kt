import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Enemy : Container() {

    lateinit var enemy: Bitmap
    val enemyHitboxes = mutableListOf<SolidRect>()
    var lives: Int = 1
    //var velocityX = 100.0
    var jumping = false
    private var velocityY: Double = 30.0
    private var velocityX: Double = 100.0

    suspend fun load() {
//        createBat(64, 400)
//        createBat(64, 500)
//        createBat(64, 600)
//        createBat(64,1300)
    }
    fun setVelocityX(velocity: Double) {
        this.velocityX = velocity
    }

    fun getVelocityX(): Double {
        return velocityX
    }

    fun setVelocityY(velocity: Double) {
        this.velocityY = velocity
    }

    fun getVelocityY(): Double {
        return velocityY
    }

    suspend fun createBat(gx:Int, gy:Int) {
        enemy = loadImage("enemy.png")
        val enemyBitmap = image(enemy) {
            smoothing = false
            position(gx, gy)
        }
        val enemyHitbox = solidRect(width = enemy.width, height = enemy.height) {
            alpha = 0.0
            position(gx, gy)
        }
        enemyHitboxes.add(enemyHitbox)
    }

}

