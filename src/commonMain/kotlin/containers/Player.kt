package containers

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import loadImage

class Player : Container() {

    enum class State {
        LOAD,
        LIVE,
        MOVING,
        HURT
    }

    var lives = 3
    var jumping = false
    var jumpForce = 0.0
    var jumpDistance = 0.0
    lateinit var playerBitmap: Bitmap
    lateinit var playerlBitmap: Bitmap
    lateinit var state: State

    suspend fun idle_right() {
        state = State.LOAD
        playerBitmap = loadImage("entities/player.png")
        val image = image(playerBitmap)
    }
    suspend fun idle_left() {
        state = State.LOAD
        playerlBitmap = loadImage("entities/playerl.png")
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

        fun live() {
            state = State.LIVE
            println(state)
            state = State.MOVING
        }

        fun hurt() {
            state = State.HURT
        }
    }
