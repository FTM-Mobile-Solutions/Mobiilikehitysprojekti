package scenes
import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import kotlinx.coroutines.*

class FinalScene : Scene() {
    private lateinit var bg: Image
    private lateinit var mainMenuText: Text
    override suspend fun SContainer.sceneInit() {
        val gameFont = TtfFont(resourcesVfs["font/dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            tint = Colors.LIGHTSEAGREEN
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        mainMenuText = text("Congratulations!", textSize = 32.0) {
            centerOnStage()
            tint = Colors.WHITE
            alpha = 0.0
            font = gameFont
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        coroutineScope {
        val bgTween = async { bg.tween(bg::alpha[1.0]) }
        val mainmenuTween = async { mainMenuText.tween(mainMenuText::alpha[1.0]) }
            awaitAll(bgTween, mainmenuTween)
        }
    }

    override suspend fun sceneBeforeLeaving() {
        coroutineScope {
            val bgTween = async { bg.tween(bg::alpha[0.0]) }
            val mainmenuTween = async { mainMenuText.tween(mainMenuText::alpha[0.0]) }
            awaitAll(bgTween, mainmenuTween)
        }
        super.sceneBeforeLeaving()
    }
}
