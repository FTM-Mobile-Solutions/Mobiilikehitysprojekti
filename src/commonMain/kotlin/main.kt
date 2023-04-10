import com.soywiz.korau.sound.*
import com.soywiz.korge.Korge
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korev.Key
import com.soywiz.korge.scene.*
import com.soywiz.korim.format.*
import com.soywiz.korinject.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import kotlin.reflect.*
import com.soywiz.korge.*
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import kotlin.reflect.KClass


suspend fun main() = Korge(Korge.Config(module = MainModule))

object MainModule : Module() {
    override val mainScene: KClass<out Scene>
        get() = GameScene::class
    override val title: String
        get() = "Konapeli"
    override val size: SizeInt
        get() = SizeInt(360, 800)
    override val scaleMode: ScaleMode
        get() = ScaleMode.COVER
    override val windowSize: SizeInt
        get() = SizeInt(360, 800)
    override val bgcolor: RGBA
        get() = Colors.BLACK

    override suspend fun AsyncInjector.configure() {
        mapPrototype { GameScene() }
        mapPrototype { MainScene() }
        mapPrototype { SplashScreen() }
        mapPrototype { GameOver() }
    }
}
//    val bG = image(resourcesVfs["kornertausta.png"].readBitmap()){
//    }
//    val playerVoice = resourcesVfs["konaa.wav"].readSound()
//    // camera adjust
//    val camera = fixedSizeContainer(width, height)
//    addChild(camera)
//
//    // Create a solid rectangle
//
//    val solidRect = solidRect(width = 10000000000000.0, height = 40.0, color = Colors.GREEN)
//    val solidBase = solidRect(width = 10000000000000.0, height = 200.0, color = Colors.SANDYBROWN)
//    val solidObstacle = solidRect(width = 100.0, height = 10.0, color = Colors.GREEN)
//    // Position the solid objects
//    solidRect.position(0.0, 400.0)
//    solidBase.position(0.0, 440.0)
//    solidObstacle.position(500, 300)
//
//    val player = circle(32.0, Colors.BLUE) {
//        xy(256, 256)
//    }
//
//    val enemy = Enemy(20.0, Colors.RED)
//    enemy.Espeed = 100
//    enemy.player = player
//    addChild(enemy)
//    //enemy.player = player
//    //enemy.position(600, 256)
//
//    val playerclass = Player()
//    playerclass.load()
//
//    val container = container {
//        addChild(playerclass)
//    }
//
//
//
//        // Create score text
//    val scoreText = text("Score: 0", textSize = 24.0, Colors.WHITE) {
//        xy(16.0, 16.0)
//    }
//
//    // Initialize score variable
//    var score = 0
//
//    camera.addChild(solidRect)
//    camera.addChild(solidBase)
//    camera.addChild(player)
//    camera.addChild(solidObstacle)
//
//
//    val gravity = 250.0
//
//    var velocityY = 0.0
//
//    var konaPlayed = false
//
//    fun konaSound(){
//        playerVoice.volume = 0.1 // sets the volume to 50%
//        if(!konaPlayed) {
//            launch {
//                playerVoice.play()
//                konaPlayed = true
//            }
//        }else
//            konaPlayed = false
//
//    }
//
//    addUpdater { dt ->
//
//
//        //player.x += 50.0 * dt // move the player to the right
//        enemy.update(dt)
//        velocityY += gravity * dt.seconds
//        player.y += velocityY * dt.seconds
//        if (keys[Key.LEFT]) player.x -= 256 * dt.milliseconds / 1000.0
//        if (keys[Key.LEFT]) {
//            // Check if the player is at the left edge of the screen
//            if (player.x > 0.0) {
//                player.x -= 256 * dt.milliseconds / 1000.0
//            } else {
//                // Player is at the left edge of the screen, so stop moving left
//                player.x = 0.0
//            }
//        }
//        if (keys[Key.RIGHT]) player.x += 256 * dt.milliseconds / 1000.0
//        if (keys[Key.SPACE]) player.y -= 256 * dt.milliseconds / 1000.0
//        if (keys[Key.SPACE]){
//            konaSound()
//        }
//
//        //if (keys[Key.DOWN]) player.y += 256 * dt.milliseconds / 1000.0
//        if(player.collidesWith(solidRect)){
//            player.y = solidRect.y - player.radius*2
//            velocityY = 0.0
//        }
//        camera.x = - player.x + width / 2
//        camera.y = - player.y + height / 2
//        if(camera.x > 0.0){
//            camera.x = 0.0
//        }
//        if(player.collidesWith(solidObstacle)){
//            player.y = solidObstacle.y - player.radius*2
//            velocityY = 0.0
//        }
//
//    }

