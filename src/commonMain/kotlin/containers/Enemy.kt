package containers

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import loadImage

class Enemy : Container() {

//    lateinit var enemy: Bitmap
    var z = 0
    var i = 0
    val enemyHitboxes = mutableListOf<SolidRect>()
    private val enemyBitmaps = mutableMapOf<String, Image>()
    private var velocityY: Double = 30.0
    private var velocityX: Double = 100.0

    suspend fun load() {
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
        val enemy = loadImage("entities/enemy.png")
        val enemyBitmap = image(enemy) {
            smoothing = false
            position(gx, gy)
        }
        val enemyHitbox = solidRect(width = enemy.width, height = enemy.height) {
            alpha = 0.0
            position(gx, gy)
        }
        val enemyName = "enemy: ${z++}"
        enemyBitmaps[enemyName] = enemyBitmap
        enemyHitboxes.add(enemyHitbox)
    }
    fun enemydestroyer() {
        enemyHitboxes.clear()
        for ((enemyName, enemyImage) in enemyBitmaps) {
            val enemyBitmap = enemyBitmaps[enemyName]
            enemyBitmap?.removeFromParent()
            z--
            enemyBitmaps.remove(enemyName)
            break
        }
    }

}

