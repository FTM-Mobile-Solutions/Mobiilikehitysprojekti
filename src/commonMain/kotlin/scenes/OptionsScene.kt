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
import kotlinx.coroutines.*

class OptionsScene : Scene() {
    private lateinit var bg: Image
    private lateinit var tune: SoundChannel
    private lateinit var back: Text
    private lateinit var info1: Text
    private lateinit var info2: Text
    private lateinit var info3: Text
    private lateinit var info4: Text
    private lateinit var info5: Text
    private lateinit var info6: Text
    private lateinit var info7: Text
    override suspend fun SContainer.sceneInit() {

        val gameFont = TtfFont(resourcesVfs["font/dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            smoothing = false
            alpha = 1.0
        }
        back = text("Back", textSize = 32.0) {
            position(280,420)
            tint = Colors.GHOSTWHITE
            alpha = 0.0
            font = gameFont
        }
        info1 = text("Game controls:", textSize = 28.0) {
            centerOnStage()
            y -= 150
            tint = Colors.GHOSTWHITE
            alpha = 0.0
            font = gameFont
        }
        info2 = text("Swipe or tap the preferred side.", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y -= 75
            alpha = 0.0
            font = gameFont
        }
        info3 = text("For example: ", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y -= 25
            alpha = 0.0
            font = gameFont
        }
        info4 = text("if player wants to jump right, ", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y += 25
            alpha = 0.0
            font = gameFont
        }
        info5 = text("tap or swipe from the left side.", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y += 75
            alpha = 0.0
            font = gameFont
        }
        info6 = text("or use arrow keys.", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y += 125
            alpha = 0.0
            font = gameFont
        }
        info7 = text("jump by releasing the key.", textSize = 24.0) {
            centerOnStage()
            tint = Colors.GHOSTWHITE
            y += 175
            alpha = 0.0
            font = gameFont
        }
        back.onClick {
            sceneContainer.changeTo<MainScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["sfx/gamesong.wav"].readMusic().play()
        tune.volume = 0.1
        coroutineScope {
        val tuneTween = async { sceneContainer.tween(tune::volume[0.1])}
        val backTween = async { back.tween(back::alpha[1.0])}
        val info1Tween = async { info1.tween(info1::alpha[1.0])}
        val info2Tween = async { info2.tween(info2::alpha[1.0])}
        val info3Tween = async { info3.tween(info3::alpha[1.0])}
        val info4Tween = async { info4.tween(info4::alpha[1.0])}
        val info5Tween = async { info5.tween(info5::alpha[1.0])}
            val info6Tween = async { info6.tween(info6::alpha[1.0])}
            val info7Tween = async { info7.tween(info7::alpha[1.0])}
            awaitAll(tuneTween, backTween, info1Tween, info2Tween, info3Tween, info4Tween, info5Tween, info6Tween, info7Tween)
        }
    }

    override suspend fun sceneBeforeLeaving() {
        coroutineScope {
            val tuneTween = async { sceneContainer.tween(tune::volume[0.0])}
            val backTween = async { back.tween(back::alpha[0.0])}
            val info1Tween = async { info1.tween(info1::alpha[0.0])}
            val info2Tween = async { info2.tween(info2::alpha[0.0])}
            val info3Tween = async { info3.tween(info3::alpha[0.0])}
            val info4Tween = async { info4.tween(info4::alpha[0.0])}
            val info5Tween = async { info5.tween(info5::alpha[0.0])}
            val info6Tween = async { info4.tween(info4::alpha[1.0])}
            val info7Tween = async { info5.tween(info5::alpha[1.0])}
            awaitAll(tuneTween, backTween, info1Tween, info2Tween, info3Tween, info4Tween, info5Tween, info6Tween, info7Tween)
        }
        tune.stop()
        super.sceneBeforeLeaving()
    }
}
