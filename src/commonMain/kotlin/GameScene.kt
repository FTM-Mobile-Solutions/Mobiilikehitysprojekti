import com.soywiz.klock.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korio.async.*

class GameScene : Scene() {
    private val fieldMargin = 0
    private lateinit var player: Player
    private lateinit var level: Level
    private lateinit var camera: CameraContainer
    private var gravity = 3500.0
    private var velocityY = 0.0

    override suspend fun SContainer.sceneInit() {
        val flagimage = image(loadImage("goal.png")) {
            position(1100, 315)
            smoothing = false
        }

        camera = cameraContainer(views.virtualWidthDouble,views.virtualHeightDouble)

        level = Level()
        level.level1()


        player = Player()
        player.load()
        player.position(views.virtualWidth / 2, views.virtualHeight / 2)

//        addChild(player)
//        addChild(level)

        camera.addChild(level)
        camera.addChild(player)

        addChild(camera)

        addUpdater { update(it) }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        player.live()
    }

    private fun update(dt: TimeSpan) {
        checkInput(dt)
        checkCollisions(dt)
        checkCamerapos(dt)

        velocityY += gravity * dt.seconds // apply gravity
    }

    private fun checkCamerapos(dt: TimeSpan) {
        //camera.x = -player.x + sceneWidth / 2
        camera.y = -player.y + sceneHeight / 2
        if (camera.y != 801.0) {
            camera.y = 0.0
        }
    }
    private fun checkInput(dt: TimeSpan) {
        if (player.jumping) {
            player.moveSpeed = 200.0
        }else{
            player.moveSpeed = 100.0
        }

        velocityY += gravity * dt.seconds
        player.y += velocityY * dt.seconds
        velocityY = minOf(velocityY, 1000.0)

        if (player.state == Player.State.MOVING || player.state == Player.State.HURT) {
            if (views.input.keys[Key.LEFT]) {
                player.x -= player.moveSpeed * dt.seconds
                if (player.x < 0) player.x = 0.0
            }
            if (views.input.keys[Key.RIGHT]) {
                player.x += player.moveSpeed * dt.seconds
                if (player.x > views.virtualWidth - player.width) player.x = views.virtualWidth - player.width
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
//        if (player.y > views.virtualHeight) {
//            player.position(views.virtualWidth / 2, views.virtualHeight / 2)
//            velocityY = 100.0
//            return
//        }

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
                    player.y = +470.0
                } else if (playerBottom > hitboxTop && playerTop < hitboxTop) {
                    // Set player's y position to just above the platform hitbox
                    player.jumping = false
                    player.y = hitboxTop - player.height
                    player.setVelocityY(0.0)
                } else if (playerRight > hitboxLeft && playerLeft < hitboxLeft && playerBottom > hitboxTop && playerTop < hitboxBottom) {
                    // Set player's x position to just left of the platform hitbox
                    player.x = hitboxLeft - player.width
                    player.setVelocityX(0.0)
                    player.setVelocityY(0.0)
                } else if (playerLeft < hitboxRight && playerRight > hitboxRight && playerBottom > hitboxTop && playerTop < hitboxBottom) {
                    // Set player's x position to just right of the platform hitbox
                    player.x = hitboxRight
                    player.setVelocityX(0.0)
                    player.setVelocityY(0.0)
                }
            }
        }
    }
}
