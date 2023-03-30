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

    var lives: Int = 3
    var moveSpeed = 500.0
    var jumping = false
    private var velocityY: Double = 0.0
    private var velocityX: Double = 0.0
    lateinit var state: State

    suspend fun load() {
        state = State.LOAD
        val playerBitmap = loadImage("player.png")
        val image = image(playerBitmap)
        println(state)
    }

    suspend fun getVoice(): Sound {
        return resourcesVfs["konaa.wav"].readSound()
    }

    suspend fun konaSound() {
//        val playerVoice = getVoice()
//        playerVoice.volume = 0.1 // sets the volume to 10%
//        playerVoice.play()
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
}
