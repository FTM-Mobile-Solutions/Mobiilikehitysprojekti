import com.soywiz.klock.*
import com.soywiz.korau.sound.*
import com.soywiz.korev.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*

class GameScene : Scene() {
    private val fieldMargin = 0
    private lateinit var player: Player
    private lateinit var enemy: Enemy
    private lateinit var level: Level
    private lateinit var camera: CameraContainer
    private lateinit var go: GameOver
    private lateinit var ms: MainScene
    private lateinit var tune: SoundChannel
    private var gravity = 3500.0
    private var velocityY = 0.0
    var isOnGround = false
    var isOnPlatform = false
    var facingRight = false
    private var gameOver = false
    private var playerHit = false

    override suspend fun SContainer.sceneInit() {
        camera = cameraContainer(views.virtualWidthDouble,views.virtualHeightDouble)

        level = Level()
        level.level1()


        player = Player()
        player.load()
        player.position(120, 1370)

        enemy = Enemy()
        enemy.load()
//        addChild(player)
//        addChild(level)

        camera.addChild(level)
        camera.addChild(player)
        camera.addChild(enemy)

        addChild(camera)

        addUpdater { update(it) }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["gamesong.wav"].readMusic().playForever()
        tune.volume = 0.0
        sceneContainer.tween(tune::volume[0.8], time = 1.5.seconds)
        player.live()
    }

    private fun update(dt: TimeSpan) {
        if (gameOver) {
            return // Stop updating the game
        }
        checkInput(dt)
        checkCollisions(dt)
        checkCamerapos(dt)
        enemyMovement(dt)
    }

    private suspend fun stop() {
        gameOver = true
        sceneContainer.changeTo<GameOver>()
    }

    private fun enemyMovement(dt: TimeSpan) {
        val enemyVelocityX = enemy.getVelocityX()
        val enemyVelocityY = enemy.getVelocityY()
        enemy.x += enemyVelocityX * dt.seconds
        enemy.y += enemyVelocityY * dt.seconds

        if(enemy.x > 230 && enemyVelocityX > 0){
            enemy.setVelocityX(-enemyVelocityX)
        } else if (enemy.x < -20 && enemyVelocityX < 0){
            enemy.setVelocityX(-enemyVelocityX)
        }

        if(enemy.y > 10 && enemyVelocityY > 0){
            enemy.setVelocityY(-enemyVelocityY)
        } else if(enemy.y < 0 && enemyVelocityY < 0){
            enemy.setVelocityY(-enemyVelocityY)
        }
    }

    private fun checkCamerapos(dt: TimeSpan) {
        camera.y = -player.y + sceneHeight / 2
        if (player.y > 1150.0) {
            camera.y = -336.0
        }
        else if (player.y < 400.0) {
            camera.y = 400.0
        }
    }
    private fun checkInput(dt: TimeSpan) {
        velocityY += gravity * dt.seconds
        player.y += velocityY * dt.seconds
        velocityY = minOf(velocityY, 1000.0)

        if (views.input.keys.justPressed(Key.RIGHT) && !player.jumping) {
            player.jumping = true
            facingRight = true
            player.jumpForce = 500.0
            player.jumpDistance = 100.0
        }

        if (views.input.keys.pressing(Key.RIGHT) && !player.jumping) {
            player.jumping = true
            player.jumpForce += 250 * dt.seconds
            player.jumpDistance += 100 * dt.seconds
            if (player.jumpForce >= 1250)
                player.jumpForce = 1250.0
            if (player.jumpDistance >= 350)
                player.jumpDistance = 350.0
            println("force:"+player.jumpForce)
            println("distance:"+player.jumpDistance)
        }

        if (views.input.keys.justReleased(Key.RIGHT) && !player.jumping) {
            player.jumping = true
            velocityY = -player.jumpForce
        }

        if (!isOnGround && facingRight) {
            player.x += player.jumpDistance * dt.seconds
        }

        if (!isOnGround && !facingRight) {
            player.x -= player.jumpDistance * dt.seconds
        }

        if (views.input.keys.justPressed(Key.LEFT) && !player.jumping) {
            player.jumping = true
            facingRight = false
            player.jumpForce = 500.0
            player.jumpDistance = 100.0

        }

        if (views.input.keys.pressing(Key.LEFT) && !player.jumping) {
            player.jumping = true
            player.jumpForce += 250 * dt.seconds
            player.jumpDistance += 100 * dt.seconds
            if (player.jumpForce >= 1250)
                player.jumpForce = 1250.0
            if (player.jumpDistance >= 350)
                player.jumpDistance = 350.0
            println("force:"+player.jumpForce)
            println("distance:"+player.jumpDistance)
        }

        if (views.input.keys.justReleased(Key.LEFT) && !player.jumping) {
            player.jumping = true
            velocityY = -player.jumpForce
        }
//        if (player.state == Player.State.MOVING || player.state == Player.State.HURT) {
//            if (views.input.keys[Key.LEFT]) {
//                player.x -= player.moveSpeed * dt.seconds
//                if (player.x < 50) player.x = 30.0
//                if (player.collidesWith(level.leftwallHitbox)) {
//                    player.x = level.leftwallHitbox.x + level.leftwallHitbox.width
//                }
//            }
//            if (views.input.keys[Key.RIGHT]) {
//                player.x += player.moveSpeed * dt.seconds
//                if (player.x > views.virtualWidth - player.width - 50) player.x = views.virtualWidth - player.width - 30.0
//                if (player.collidesWith(level.rightwallHitbox)) {
//                    player.x = level.rightwallHitbox.x - player.width
//                }
//            }
//            if (views.input.keys[Key.SPACE] && !player.jumping) {
//                player.jumping = true
//                velocityY = -1500.0
//                launch {
//                    player.konaSound()
//                }
//            }
//        }
    }


    private fun checkCollisions(dt: TimeSpan) {
        isOnGround = player.collidesWith(level.groundHitbox)
        if (isOnGround) {
            player.y = level.groundHitbox.y - player.height
            player.jumping = false // reset jumping flag
            isOnPlatform = false
        }
        for (hitbox in level.platformHitboxes) {
            isOnPlatform = player.collidesWith(hitbox)
            if (isOnPlatform) {
                val playerTop = player.y
                val playerBottom = player.y + player.height
                val playerLeft = player.x
                val playerRight = player.x + player.width
                val hitboxTop = hitbox.y
                val hitboxBottom = hitbox.y + hitbox.height
                val hitboxLeft = hitbox.x
                val hitboxRight = hitbox.x + hitbox.width

                if (player.collidesWith(enemy)) {
                    if (!playerHit) {
                        playerHit = true
                        player.loseHealth()
                        launch {
                            delay(1.seconds)
                            playerHit = false
                        }
                    }
                    if(player.lives == 0) {
                        launch {
                            stop()
                        }

                    }
                }
                if (playerTop < hitboxBottom && playerBottom > hitboxBottom) {
                    // Set player's y position to just below the platform hitbox
                    player.y = hitboxBottom
                    velocityY = -velocityY / 2
                } else if (playerBottom > hitboxTop && playerTop < hitboxTop) {
                    // Set player's y position to just above the platform hitbox
                    player.jumping = false
                    isOnGround = true
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
        if (player.collidesWith(level.rightwallHitbox)) {
                    player.x = level.rightwallHitbox.x - player.width
        }
        if (player.collidesWith(level.leftwallHitbox)) {
                    player.x = level.leftwallHitbox.x + player.width * 2
        }
    }

    override suspend fun sceneBeforeLeaving() {
        sceneContainer.tween(tune::volume[0.0], time = .4.seconds)
        tune.stop()
        super.sceneBeforeLeaving()
    }
}

