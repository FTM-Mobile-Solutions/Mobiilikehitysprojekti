package scenes

import com.soywiz.klock.*
import com.soywiz.korau.sound.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class GameOver: Scene() {
    private lateinit var bg: Image
    private lateinit var go: Image
    private lateinit var playAgainText: Text
    private lateinit var mainMenuText: Text
    private lateinit var gtune: SoundChannel
    override suspend fun SContainer.sceneInit() {
        val gameFont = TtfFont(resourcesVfs["font/dpcomic.ttf"].readAll())
        //tune = resourcesVfs["gameover.wav"].readMusic().play()
        //tune.volume = 0.1
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            tint = Colors.DARKSLATEGRAY
            smoothing = false
            alpha = 0.0

        }

        go = image(resourcesVfs["miscellaneous/gameover.png"].readBitmap()) {
            //position(views.virtualWidth/2, views.virtualHeight/2)
            centerOnStage()
            y -= 100
            smoothing = false
            alpha = 0.0
        }

        playAgainText = text("Play Again?", textSize = 32.0) {
            centerOnStage()
            tint = Colors.DARKRED
            y += 25.0
            alpha = 0.0
            font = gameFont
        }

        mainMenuText = text("Main Menu", textSize = 32.0) {
            centerOnStage()
            tint = Colors.DARKRED
            y += 75
            alpha = 0.0
            font = gameFont
        }

        playAgainText.onClick {
            sceneContainer.changeTo<GameScene>()
        }

        mainMenuText.onClick {
            sceneContainer.changeTo<MainScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        gtune = resourcesVfs["sfx/gameover.wav"].readMusic().play()
        gtune.volume = 0.1
        sceneContainer.tween(gtune::volume[0.1], time = 1.5.seconds)
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        go.tween(go::alpha[1.0], go::scale[1.0], time = 1.seconds)
        playAgainText.tween(playAgainText::alpha[1.0], time = 1.seconds)
        mainMenuText.tween(mainMenuText::alpha[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .4.seconds)
        go.tween(go::alpha[0.0], go::scale[0.0], time = .4.seconds)
        playAgainText.tween(playAgainText::alpha[0.0], time = .4.seconds)
        mainMenuText.tween(mainMenuText::alpha[0.0], time = .4.seconds)
        sceneContainer.tween(gtune::volume[0.0], time = .4.seconds)
        gtune.stop()
        super.sceneBeforeLeaving()
    }
}
