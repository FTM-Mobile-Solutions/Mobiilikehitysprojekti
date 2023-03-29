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
    var moveSpeed = 300.0
    var isOnGround = false
    private var velocityY = 0.0
    private val jumpSpeed = -2000.0
    private val gravity = 5000.0
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

    fun jump() {
        if (isOnGround) {
            velocityY = jumpSpeed
            state = State.JUMPING
            isOnGround = false
        }
    }
}
