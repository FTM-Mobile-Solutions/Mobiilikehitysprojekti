import com.soywiz.klock.*
import com.soywiz.korau.sound.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    private val fieldMargin = 15

    private lateinit var bg: Bitmap
    private lateinit var ground: Bitmap
    private lateinit var platform: Bitmap
    private lateinit var player: Player
    private lateinit var groundHitbox: SolidRect
    private lateinit var platformHitbox: SolidRect

    private var  gravity = 7000.0
    private var  velocityY = 0.0

    override suspend fun SContainer.sceneInit() {

        bg = loadImage("testibg.png")
        val bgimage = image(bg) {
            smoothing = false
        }
        ground = loadImage("platformtest.png")
        val groundimage = image(ground) {
            smoothing = false
            position(0, 500)
        }
        platform = loadImage("konaplatformtest.png")
        val platformimage = image(platform) {
            smoothing = false
            position(720, 350)
        }
        groundHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 500)
        }
        platformHitbox = solidRect(width = platform.width, height = platform.height) {
            alpha = 0.0
            position(720, 350)
        }

        addChild(platformHitbox)
        addChild(groundHitbox)
        player = Player()
        player.load()
        player.position(views.virtualWidth / 2, views.virtualHeight / 2)
        addChild(player)

        //camera adjust, not working currently
//        val camera = fixedSizeContainer(width, height)
//        addChild(camera)
//        camera.addChild(player)



        addUpdater { update(it) }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        player.live()
    }

    private fun update(dt: TimeSpan) {
        checkInput(dt)
        checkCollisions(dt)
    }
    private fun checkInput(dt: TimeSpan) {
        var g = gravity
        var vel = velocityY

        vel += g * dt.seconds
        player.y += vel * dt.seconds

        if (player.state == Player.State.MOVING || player.state == Player.State.HURT) {
            if (views.input.keys[Key.LEFT]) {
                if (player.x > fieldMargin) player.x -= player.moveSpeed * dt.seconds
            }
            if (views.input.keys[Key.RIGHT]) {
                if (player.x < views.virtualWidth - fieldMargin) player.x += player.moveSpeed * dt.seconds
            }
            if (views.input.keys[Key.SPACE]) {
                if (player.y > fieldMargin) {
                    player.y -= player.moveSpeed * dt.seconds
                }
                launch {
                    player.konaSound()
                }
            }
        }
    }
    private fun checkCollisions(dt: TimeSpan) {
        if (player.collidesWith(groundHitbox)) {
            if (player.y + player.height > groundHitbox.y) {
                player.y = groundHitbox.y - player.height
            }
        }
        if (player.collidesWith(platformHitbox)) {
            if (player.y + player.height > platformHitbox.y) {
                player.y = platformHitbox.y - player.height
            }
        }
    }
}
