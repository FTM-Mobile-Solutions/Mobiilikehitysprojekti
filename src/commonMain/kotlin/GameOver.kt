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
    private lateinit var playAgainText: Text
    private lateinit var mainMenuText: Text
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

        playAgainText = text("Play Again?", textSize = 24.0) {
            centerOnStage()
            tint = Colors.RED
            y += 100.0
            alpha = 0.0
        }

        mainMenuText = text("Main Menu", textSize = 24.0) {
            centerOnStage()
            tint = Colors.RED
            y += 150
            alpha = 0.0
        }

        playAgainText.onClick {
            sceneContainer.changeTo<GameScene>()
        }

        mainMenuText.onClick {
            sceneContainer.changeTo<MainScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        go.tween(go::alpha[1.0], go::scale[1.0], time = 1.seconds)
        playAgainText.tween(playAgainText::alpha[1.0], time = 1.seconds)
        mainMenuText.tween(mainMenuText::alpha[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        go.tween(go::alpha[0.0], go::scale[0.0], time = .5.seconds)
        playAgainText.tween(playAgainText::alpha[0.0], time = .5.seconds)
        mainMenuText.tween(mainMenuText::alpha[0.0], time = .5.seconds)
    }
}
