package scenes
import com.soywiz.klock.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import containers.*
import kotlinx.coroutines.*

class FinalScene : Scene() {
    private lateinit var sound: SceneSound
    private lateinit var bg: Image
    private lateinit var mainMenuText: Text
    private lateinit var returnText: Text
    override suspend fun SContainer.sceneInit() {
        sound = SceneSound()
        val gameFont = TtfFont(resourcesVfs["font/dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            tint = Colors.LIGHTSEAGREEN
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        mainMenuText = text(" Congratulations!", textSize = 48.0) {
            centerOnStage()
            y -= 100
            tint = Colors.GOLD
            alpha = 0.0
            font = gameFont
        }
        returnText = text("Return to main menu", textSize = 28.0) {
            centerOnStage()
            y += 50
            tint = Colors.WHITE
            alpha = 0.0
            font = gameFont
        }
        returnText.onClick {
            sound.navSound()
            sceneContainer.changeTo<MainScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        coroutineScope {
        val bgTween = async { bg.tween(bg::alpha[1.0]) }
        val mainmenuTween = async { mainMenuText.tween(mainMenuText::alpha[1.0]) }
            val returnTween = async { returnText.tween(returnText::alpha[1.0], time = 1.seconds) }
            awaitAll(bgTween, mainmenuTween, returnTween)
        }
    }

    override suspend fun sceneBeforeLeaving() {
        coroutineScope {
            val bgTween = async { bg.tween(bg::alpha[0.0]) }
            val mainmenuTween = async { mainMenuText.tween(mainMenuText::alpha[0.0]) }
            val returnTween = async { returnText.tween(returnText::alpha[0.0]) }
            awaitAll(bgTween, mainmenuTween, returnTween)
        }
        super.sceneBeforeLeaving()
    }
}
