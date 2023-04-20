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

class MainScene : Scene() {
    private lateinit var bg: Image
    private lateinit var title: Text
    private lateinit var playButton: Image
    private lateinit var optionsButton: Image
    private lateinit var tune: SoundChannel

    override suspend fun SContainer.sceneInit() {
        val gameFont = TtfFont(resourcesVfs["dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        playButton = image(resourcesVfs["startbutton.png"].readBitmap()) {
            //position(views.virtualWidth/2, views.virtualHeight/2)
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        title = text("ArthuR", textSize = 104.0) {
            position(50, 625)
            smoothing = false
            tint = Colors.WHITE
            font = gameFont
            alpha = 0.0
        }
        optionsButton = image(resourcesVfs["optionsbutton.png"].readBitmap()) {
            position(280,420)
            //centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        playButton.onClick {
            sceneContainer.changeTo<GameScene>()
        }
        optionsButton.onClick {
            sceneContainer.changeTo<OptionsScene>()
        }
    }
    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["gamesong.wav"].readMusic().playForever()
        tune.volume = 0.0
        sceneContainer.tween(tune::volume[0.2], time = 1.5.seconds)
        bg.tween(bg::alpha[1.0], time = 1.seconds)
        title.tween(title::alpha[1.0], time = 1.seconds)
        playButton.tween(playButton::alpha[1.0], playButton::scale[1.0], time = 1.seconds)
        optionsButton.tween(optionsButton::alpha[1.0], optionsButton::scale[1.0], time = 1.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .5.seconds)
        playButton.tween(playButton::alpha[0.0], playButton::scale[0.0], time = .5.seconds)
        title.tween(title::alpha[0.0], time = 1.seconds)
        optionsButton.tween(optionsButton::alpha[0.0], optionsButton::scale[0.0], time = .5.seconds)
        sceneContainer.tween(tune::volume[0.0], time = .4.seconds)
        tune.stop()
        super.sceneBeforeLeaving()
    }
}
