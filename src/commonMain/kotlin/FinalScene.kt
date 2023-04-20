
import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class FinalScene : Scene() {
    private lateinit var bg: Image
    private lateinit var mainMenuText: Text
    override suspend fun SContainer.sceneInit() {
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            tint = Colors.LIGHTSEAGREEN
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        mainMenuText = text("Hyvin pelattu!", textSize = 24.0) {
            centerOnStage()
            tint = Colors.RED
            alpha = 0.0
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        mainMenuText.tween(mainMenuText::alpha[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        mainMenuText.tween(mainMenuText::alpha[0.0], time = .4.seconds)
        super.sceneBeforeLeaving()
    }
}
