package containers

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import loadImage

class Enemy : Container() {

    lateinit var enemy: Bitmap
    val enemyHitboxes = mutableListOf<SolidRect>()
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

    fun enemydestroyer() {
        enemyHitboxes.clear()
    }

    suspend fun createBat(gx:Int, gy:Int) {
        enemy = loadImage("entities/enemy.png")
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

