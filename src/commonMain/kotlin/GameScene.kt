import com.soywiz.klock.*
import com.soywiz.korau.sound.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    private val fieldMargin = 15

    private lateinit var bg: Bitmap
    private lateinit var player: Player

    override suspend fun SContainer.sceneInit() {

        bg = loadImage("testibg.png")
        val image = image(bg) {
            smoothing = false
        }

        val solidRect = solidRect(width = 100.0, height = 40.0, color = Colors.GREEN).xy(views.virtualWidth / 2, 145)

        player = Player()
        player.load()
        player.position(views.virtualWidth / 2, views.actualHeight / 2)
        addChild(player)

        addUpdater { update(it) }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        player.live()
    }

    private fun update(dt: TimeSpan) {
        checkInput(dt)
    }
    private fun checkInput(dt: TimeSpan) {

        if (player.state == Player.State.MOVING || player.state == Player.State.HURT) {
            if (views.input.keys[Key.LEFT]) {
                if (player.x > fieldMargin) player.x -= player.moveSpeed * dt.seconds
            }
            if (views.input.keys[Key.RIGHT]) {
                if (player.x > fieldMargin) player.x += player.moveSpeed * dt.seconds
            }
        }
    }
}
