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
            //position(50,50)
        }
    }

    fun live() {
        state = State.LIVE
    }

    fun moving() {
        state = State.MOVING
    }

    fun hurt() {
        state = State.HURT
    }
}
