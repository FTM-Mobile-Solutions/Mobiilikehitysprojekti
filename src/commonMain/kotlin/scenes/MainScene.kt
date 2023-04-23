package scenes

import com.soywiz.korau.sound.*
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

class MainScene : Scene() {
    private var clicked = false
    private lateinit var sound: SceneSound
    private lateinit var bg: Image
    private lateinit var title: Text
    private lateinit var playButton: Image
    private lateinit var optionsButton: Image
    private lateinit var tune: SoundChannel

    override suspend fun SContainer.sceneInit() {
        val gameFont = TtfFont(resourcesVfs["font/dpcomic.ttf"].readAll())
        sound = SceneSound()
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            smoothing = false
            alpha = 1.0
        }
        playButton = image(resourcesVfs["miscellaneous/startbutton.png"].readBitmap()) {
            //position(views.virtualWidth/2, views.virtualHeight/2)
            centerOnStage()
            y += 25
            smoothing = false
            alpha = 0.0
        }
        title = text("ArthuR", textSize = 104.0) {
            position(50, 625)
            smoothing = false
            tint = Colors.GOLD
            font = gameFont
            alpha = 0.0
        }
        optionsButton = image(resourcesVfs["miscellaneous/optionsbutton.png"].readBitmap()) {
            position(280,420)
            //centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        playButton.onClick {
            sound.navSound()
            clicked = true
            sceneContainer.changeTo<GameScene>()
        }
        optionsButton.onClick {
            sound.navSound()
            sceneContainer.changeTo<OptionsScene>()
        }
    }
    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["sfx/gamesong.wav"].readMusic().playForever()
        tune.volume = 0.0
        coroutineScope {
            val sceneContainerTween = async {sceneContainer.tween(tune::volume[0.2])}
            val bgTween = async {bg.tween(bg::alpha[1.0])}
            val titleTween = async {title.tween(title::alpha[1.0])}
            val playButtonTween = async {playButton.tween(playButton::alpha[1.0])}
            val optionsButtonTween = async {optionsButton.tween(optionsButton::alpha[1.0])}
            awaitAll(sceneContainerTween, bgTween, titleTween, playButtonTween, optionsButtonTween)
        }
    }

    override suspend fun sceneBeforeLeaving() {
        coroutineScope {
            val titleTween = async {title.tween(title::alpha[0.0])}
            val playButtonTween = async {playButton.tween(playButton::alpha[0.0])}
            val optionsButtonTween = async {optionsButton.tween(optionsButton::alpha[0.0])}
            awaitAll(titleTween, playButtonTween, optionsButtonTween)
            if (clicked) {
                bg.tween(bg::alpha[0.0])
            }
        }
        tune.stop()
        super.sceneBeforeLeaving()
    }
}
