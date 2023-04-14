import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*

class Player : Container() {
    enum class State {
        LOAD,
        LIVE,
        MOVING,
        JUMPING,
        HURT
    }

    var lives = 3
    var moveSpeed = 100.0
    var jumping = false
    var jumpForce = 0.0
    var jumpDistance = 0.0
    private var velocityY: Double = 0.0
    private var velocityX: Double = 0.0
    lateinit var state: State

    suspend fun load() {
        state = State.LOAD
        val playerBitmap = loadImage("player.png")
        val image = image(playerBitmap)
        println(state)
    }

    fun loseHealth() {
        lives -= 1
        // Do something when the player loses health, e.g. play a sound or show a visual effect.
        println(lives)
    }

    suspend fun getVoice(): Sound {
        return resourcesVfs["konaa.wav"].readSound()
    }

    suspend fun konaSound() {
        val playerVoice = getVoice()
        playerVoice.volume = 0.1 // sets the volume to 10%
        playerVoice.play()
    }

    fun setVelocityX(velocity: Double) {
        this.velocityX = velocity
    }

    fun setVelocityY(velocity: Double) {
        this.velocityY = velocity
    }

    /*fun getVelocityY(): Double {
        return velocityY
    }*/

    /*fun isOnGround(): Boolean {
        return isOnGround
    }*/


    fun live() {
        state = State.LIVE
        println(state)
        state = State.MOVING
    }

    fun moving() {
        state = State.MOVING

        println(state)
    }

    fun hurt() {
        state = State.HURT
    }

    suspend fun createhearts() {
        val heartWidth = 30
        val heartImages = mutableListOf<Image>()

        for (i in 0..2) {
            val heart = loadImage("heart.png")
            val heartImage = image(heart) {
                position(40 + i * heartWidth, 350)
            }
            heartImages.add(heartImage)
        }
    }
}
