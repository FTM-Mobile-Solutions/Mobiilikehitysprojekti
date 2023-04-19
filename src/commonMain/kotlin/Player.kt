import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
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
    var jumping = false
    var jumpForce = 0.0
    var jumpDistance = 0.0
    lateinit var playerBitmap: Bitmap
    lateinit var playerlBitmap: Bitmap
    lateinit var playerleftBitmap: Bitmap
    lateinit var playerrightBitmap: Bitmap
    private var velocityY: Double = 0.0
    private var velocityX: Double = 0.0
    lateinit var state: State

    suspend fun idle_right() {
        state = State.LOAD
        playerBitmap = loadImage("player.png")
        val image = image(playerBitmap)
    }
    suspend fun idle_left() {
        state = State.LOAD
        playerlBitmap = loadImage("playerl.png")
        val image = image(playerlBitmap)
    }

        fun loseHealth() {
            lives -= 1
            println(lives)
        }

        /*suspend fun getVoice(): Sound {
            return resourcesVfs["konaa.wav"].readSound()
        }
        suspend fun konaSound() {
            val playerVoice = getVoice()
            playerVoice.volume = 0.1 // sets the volume to 10%
            playerVoice.play()
        } */

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

        suspend fun left() {
            playerleftBitmap = loadImage("player_left.png")
            val image = image(playerleftBitmap)
        }
        suspend fun right() {
            playerrightBitmap = loadImage("player_right.png")
            val image = image(playerrightBitmap)
    }
    suspend fun jumpleft() {
        playerleftBitmap = loadImage("player_jumping_left.png")
        val image = image(playerleftBitmap)
    }
    suspend fun jumpright() {
        playerrightBitmap = loadImage("player_jumping_right.png")
        val image = image(playerrightBitmap)
    }

        fun hurt() {
            state = State.HURT
        }
    }
