import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameOver: Scene() {
    private lateinit var bg: Image
    private lateinit var go: Image
    override suspend fun SContainer.sceneInit() {

        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            tint = Colors.DARKSLATEGRAY
            smoothing = false
            alpha = 0.0
        }

        go = image(resourcesVfs["gameover.png"].readBitmap()) {
            //position(views.virtualWidth/2, views.virtualHeight/2)
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        go.onClick {
            sceneContainer.changeTo<GameScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        go.tween(go::alpha[1.0], go::scale[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        go.tween(go::alpha[0.0], go::scale[0.0], time = .5.seconds)
    }
}
