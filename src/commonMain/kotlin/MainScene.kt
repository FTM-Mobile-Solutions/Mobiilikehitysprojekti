import com.soywiz.klock.*
import com.soywiz.korge.animate.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.onClick
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*

class MainScene : Scene() {
    private lateinit var bg: Image
    private lateinit var playButton: Image

    override suspend fun SContainer.sceneInit() {
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            smoothing = false
            alpha = 0.0
        }
        playButton = image(resourcesVfs["startbutton.png"].readBitmap()) {
            //position(views.virtualWidth/2, views.virtualHeight/2)
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        playButton.onClick {
            sceneContainer.changeTo<GameScene>()
        }
    }
    override suspend fun sceneAfterInit() {
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        playButton.tween(playButton::alpha[1.0], playButton::scale[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        playButton.tween(playButton::alpha[0.0], playButton::scale[0.0], time = .5.seconds)
    }
}
