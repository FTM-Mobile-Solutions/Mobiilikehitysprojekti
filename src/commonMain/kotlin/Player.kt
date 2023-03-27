import com.soywiz.korge.view.*

class Player : Container() {

    enum class State {
        LOAD,
        LIVE,
        MOVING,
        HURT
    }

    var lives: Int = 3
    var moveSpeed = 100.0
    lateinit var state: State

    suspend fun load() {
        state = State.LOAD
        val playerBitmap = loadImage("player.png")
        val image = image(playerBitmap) {
            //position(500,500)
        }
        println(state)
    }

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
