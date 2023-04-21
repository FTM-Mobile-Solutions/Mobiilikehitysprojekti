package containers

import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korio.file.std.*
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

        suspend fun getJump(): Sound {
            return resourcesVfs["sfx/jump.wav"].readSound()
        }
        suspend fun jumpSound() {
            val jump = getJump()
                jump.volume = 0.7
                jump.play()
        }
        suspend fun getLimit(): Sound {
            return resourcesVfs["sfx/limit.wav"].readSound()
        }
        suspend fun limitSound() {
            val limit = getLimit()
            limit.volume = 0.7
            limit.play()
        }
        suspend fun getHurt(): Sound {
            return resourcesVfs["sfx/impact.wav"].readSound()
        }
        suspend fun hurtSound() {
            val hurt = getHurt()
            hurt.play()
        }

        suspend fun getDoor(): Sound {
            return resourcesVfs["sfx/door.wav"].readSound()
        }
        suspend fun doorSound() {
            val landing = getDoor()
            landing.volume = 0.5
            landing.play()
        }
        fun live() {
            state = State.LIVE
            println(state)
            state = State.MOVING
        }

        fun hurt() {
            state = State.HURT
        }
    }
