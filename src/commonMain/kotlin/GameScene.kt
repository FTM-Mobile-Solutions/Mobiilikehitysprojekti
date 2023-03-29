import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korio.async.*

class GameScene : Scene() {
    private val fieldMargin = 15

//    private lateinit var bg: Bitmap
//    private lateinit var ground: Bitmap
//    private lateinit var platform: Bitmap
    private lateinit var player: Player
    private lateinit var level: Level
//    private lateinit var groundHitbox: SolidRect
//    private lateinit var platformHitbox: SolidRect

    private var gravity = 5000.0
    private var velocityY = 100.0

    override suspend fun SContainer.sceneInit() {

        val flagimage = image(loadImage("goal.png")) {
            position(1100, 315)
            smoothing = false
        }

        level = Level()
        level.level1()

        player = Player()
        player.load()
        player.position(views.virtualWidth / 2, views.virtualHeight / 2)

        addChild(level)
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
        val isOnGround = player.collidesWith(level.groundHitbox)

        if (isOnGround) {
            // Set player's y position to just above the ground hitbox
            player.y = level.groundHitbox.y - player.height
        }

        for (hitbox in level.platformHitboxes) {
            if (player.collidesWith(hitbox)) {
                val playerTop = player.y
                val playerBottom = player.y + player.height
                val playerX = player.y
                val hitboxTop = hitbox.y
                val hitboxBottom = hitbox.y + hitbox.height
                val hitBoxSide = hitbox.x

                if (playerTop < hitboxBottom && playerBottom > hitboxBottom) {
                    // Set player's y position to just below the platform hitbox
                    player.y = hitboxBottom
                } else if (playerBottom > hitboxTop && playerTop < hitboxTop) {
                    // Set player's y position to just above the platform hitbox
                    player.y = hitboxTop - player.height
                } else if(playerX == hitBoxSide) // ei toimi oikein vielä
                    player.x = hitBoxSide - player.height
            }
        }
    }

}
