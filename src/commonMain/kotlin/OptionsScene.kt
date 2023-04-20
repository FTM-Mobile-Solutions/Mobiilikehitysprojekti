
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

class OptionsScene : Scene() {
    private lateinit var bg: Image
    private lateinit var tune: SoundChannel
    private lateinit var back: Text
    private lateinit var info1: Text
    private lateinit var info2: Text
    private lateinit var info3: Text
    private lateinit var info4: Text
    private lateinit var info5: Text
    override suspend fun SContainer.sceneInit() {

        val gameFont = TtfFont(resourcesVfs["dpcomic.ttf"].readAll())
        bg = image(resourcesVfs["tiles/bg.png"].readBitmap()) {
            centerOnStage()
            smoothing = false
            alpha = 0.0
        }
        back = text("Back", textSize = 32.0) {
            position(280,420)
            tint = Colors.GHOSTWHITE
            alpha = 0.0
            font = gameFont
        }
        info1 = text("Game controls:", textSize = 24.0) {
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
        back.onClick {
            sceneContainer.changeTo<MainScene>()
        }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["gamesong.wav"].readMusic().play()
        tune.volume = 0.1
        sceneContainer.tween(tune::volume[0.1], time = 1.5.seconds)
        bg.tween(bg::alpha[1.0], time = 0.7.seconds)
        back.tween(back::alpha[1.0], time = 0.7.seconds)
        info1.tween(info1::alpha[1.0], time = 0.7.seconds)
        info2.tween(info2::alpha[1.0], time = 0.7.seconds)
        info3.tween(info3::alpha[1.0], time = 0.7.seconds)
        info4.tween(info4::alpha[1.0], time = 0.7.seconds)
        info5.tween(info5::alpha[1.0], time = 0.7.seconds)
    }

    override suspend fun sceneBeforeLeaving() {
        bg.tween(bg::alpha[0.0], time = .4.seconds)
        back.tween(back::alpha[0.0], time = .4.seconds)
        info1.tween(info1::alpha[0.0], time = .4.seconds)
        info2.tween(info2::alpha[0.0], time = .4.seconds)
        info3.tween(info3::alpha[0.0], time = .4.seconds)
        info4.tween(info4::alpha[0.0], time = .4.seconds)
        info5.tween(info5::alpha[0.0], time = .4.seconds)
        sceneContainer.tween(tune::volume[0.0], time = .4.seconds)
        tune.stop()
        super.sceneBeforeLeaving()
    }
}
