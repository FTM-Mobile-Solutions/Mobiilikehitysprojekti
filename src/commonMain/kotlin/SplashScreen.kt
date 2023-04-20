import com.soywiz.klock.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korim.font.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class SplashScreen : Scene() {
    private lateinit var bg: Image
    private lateinit var splash: Text
    private lateinit var splashpresent: Text

    override suspend fun SContainer.sceneInit() {
        val gameFont = TtfFont(resourcesVfs["dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            tint = Colors.LIGHTSKYBLUE
            smoothing = false
            alpha = 0.0
        }
        splash = text(" FTM", textSize = 124.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            alpha = 0.0
            font = gameFont
        }
        splashpresent = text("presents", textSize = 32.0) {
            position(125, 825)
            tint = Colors.GHOSTWHITE
            alpha = 0.0
            font = gameFont
        }
    }

    override suspend fun sceneAfterInit() {
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        splash.tween(splash::alpha[1.0])
        splashpresent.tween(splashpresent::alpha[1.0])
        delay(2.seconds)
        splash.tween(splash::alpha[0.0])
        splashpresent.tween(splashpresent::alpha[0.0])
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        sceneContainer.changeTo<MainScene>()
    }

    override suspend fun sceneBeforeLeaving() {
        //sceneContainer.tween(bgMusic::volume[0.0], time = .4.seconds)
        //bgMusic.stop()
    }
}
