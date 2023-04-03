import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class SplashScreen : Scene() {
    private lateinit var bg: Image

    override suspend fun SContainer.sceneInit() {

        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            tint = Colors.DARKVIOLET
            smoothing = false
            alpha = 0.0
        }
    }

    override suspend fun sceneAfterInit() {
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        delay(2.seconds)
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        sceneContainer.changeTo<MainScene>()
    }

    override suspend fun sceneBeforeLeaving() {
        //sceneContainer.tween(bgMusic::volume[0.0], time = .4.seconds)
        //bgMusic.stop()
    }
}
