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
        velocityY += gravity * dt.seconds // apply gravity
    }

    private fun checkInput(dt: TimeSpan) {
        var g = gravity
        velocityY += g * dt.seconds
        player.y += velocityY * dt.seconds
        velocityY = minOf(velocityY, 1000.0)

        if (player.state == Player.State.MOVING || player.state == Player.State.HURT) {
            if (views.input.keys[Key.LEFT]) {
                if (player.x > fieldMargin) player.x -= player.moveSpeed * dt.seconds
            }
            if (views.input.keys[Key.RIGHT]) {
                if (player.x < views.virtualWidth - fieldMargin) player.x += player.moveSpeed * dt.seconds
            }
            if (views.input.keys[Key.SPACE] && !player.jumping) {
                player.jumping = true
                velocityY = -1000.0
            }
        }
    }

    private fun checkCollisions(dt: TimeSpan) {
        val isOnGround = player.collidesWith(level.groundHitbox)

        if (isOnGround) {
            player.y = level.groundHitbox.y - player.height
            player.jumping = false // reset jumping flag
        }
        if (player.y > views.virtualHeight) {
            player.position(views.virtualWidth / 2, views.virtualHeight / 2)
            velocityY = 100.0
            return
        }

        for (hitbox in level.platformHitboxes) {
            if (player.collidesWith(hitbox)) {
                val playerTop = player.y
                val playerBottom = player.y + player.height
                val playerLeft = player.x
                val playerRight = player.x + player.width
                val hitboxTop = hitbox.y
                val hitboxBottom = hitbox.y + hitbox.height
                val hitboxLeft = hitbox.x
                val hitboxRight = hitbox.x + hitbox.width

                if (playerTop < hitboxBottom && playerBottom > hitboxBottom) {
                    // Set player's y position to just below the platform hitbox
                    player.y = hitboxBottom
                    player.setVelocityY(0.0)
                } else if (playerBottom > hitboxTop && playerTop < hitboxTop) {
                    // Set player's y position to just above the platform hitbox
                    player.y = hitboxTop - player.height
                    player.setVelocityY(0.0)
                } else if (playerRight > hitboxLeft && playerLeft < hitboxLeft && playerBottom > hitboxTop && playerTop < hitboxBottom) {
                    // Set player's x position to just left of the platform hitbox
                    player.x = hitboxLeft - player.width
                    player.setVelocityX(0.0)
                } else if (playerLeft < hitboxRight && playerRight > hitboxRight && playerBottom > hitboxTop && playerTop < hitboxBottom) {
                    // Set player's x position to just right of the platform hitbox
                    player.x = hitboxRight
                    player.setVelocityX(0.0)
                }
            }
        }
    }
}
