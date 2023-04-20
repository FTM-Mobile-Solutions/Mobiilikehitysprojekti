
import com.soywiz.klock.*
import com.soywiz.kmem.*
import com.soywiz.korau.sound.*
import com.soywiz.korev.*
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.*
import com.soywiz.korim.color.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*

class GameScene : Scene() {
    private lateinit var player: Player
    private lateinit var health: Health
    private lateinit var enemy: Enemy
    private lateinit var coin: Coin
    private lateinit var level: Level
    private lateinit var camera: CameraContainer
    private lateinit var tune: SoundChannel
    private var lvl = 0
    private var x = 0
    private var gravity = 3500.0
    private var velocityY = 0.0
    private var isOnGround = false
    private var isOnPlatform = false
    private var facingRight = false
    private var gameOver = false
    private var playerHit = false
    private lateinit var touchPad: SolidRect
    private lateinit var touchPad2: SolidRect

    override suspend fun SContainer.sceneInit() {
        camera = cameraContainer(views.virtualWidthDouble, views.virtualHeightDouble)

        level = Level()

        player = Player()
        player.right()

        health = Health()
        health.createHearts()

        coin = Coin()
        coin.load(15)

        enemy = Enemy()
        enemy.load()

        camera.addChild(level)
        camera.addChild(player)
        camera.addChild(enemy)
        camera.addChild(coin)

        addChild(camera)
        addChild(health)


        levelchanger(lvl)

        touchPad = solidRect(width = 90, height = views.virtualHeight * 2) {
            alpha = 0.5
            position(90, 400)
        }
        touchPad2 = solidRect(width = 90, height = views.virtualHeight * 2) {
            alpha = 0.5
            position(180, 400)
        }

        addUpdater { update(it) }
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
        tune = resourcesVfs["gamesong.wav"].readMusic().playForever()
        tune.volume = 0.0
        sceneContainer.tween(tune::volume[0.8], time = 1.5.seconds)
        player.live()
    }

    private suspend fun levelchanger(levelnum: Int) {
        if (levelnum == 0) {
            level.leveldestroyer()
            level.levelinit()
            lvl++
            println("Changed to level $lvl")
            levelchanger(lvl)
            x = 0
            player.position(120, 1370)

        }
        when (levelnum) {
            1 -> {
                enemy.createBat(64, 1050)
                enemy.createBat(64, 650)
                level.level1()
                level.setColor(Colors.LIGHTBLUE)
            }
            2 -> {
                level.level2()
                level.setColor(Colors.LIGHTSTEELBLUE)
            }
            4 -> {
                sceneContainer.changeTo<FinalScene>()
            }
        }
    }

    private fun update(dt: TimeSpan) {
        if (gameOver) {
            return // Stop updating the game
        }

        if (player.y >= level.groundHitbox.y) {
            player.jumping = false
            velocityY = 0.0
            player.y = level.groundHitbox.y
        }
        checkInput(dt)
        checkCollisions()
        checkCamerapos()
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

        if (enemy.x > 230 && enemyVelocityX > 0) {
            enemy.setVelocityX(-enemyVelocityX)
        } else if (enemy.x < -20 && enemyVelocityX < 0) {
            enemy.setVelocityX(-enemyVelocityX)
        }

        if (enemy.y > 10 && enemyVelocityY > 0) {
            enemy.setVelocityY(-enemyVelocityY)
        } else if (enemy.y < 0 && enemyVelocityY < 0) {
            enemy.setVelocityY(-enemyVelocityY)
        }
    }

    private fun checkCamerapos() {
        camera.y = -player.y + sceneHeight / 2
        if (player.y > 1130.0) {
            camera.y = -336.0
        } else if (player.y < 400.0) {
            camera.y = 400.0
        }
    }

    private fun checkInput(dt: TimeSpan) {
        velocityY += gravity * dt.seconds
        player.y += velocityY * dt.seconds
        velocityY = minOf(velocityY, 1000.0)

        var touchStartPos = Point()
        var touchEndPos = Point()
        var jumped = false

        fun setEPos() {
            touchEndPos = views.globalMouseXY
        }

        fun getEPos(): Point {
            println("konapask" + touchEndPos)
            return touchEndPos
        }

        fun setPos() {
            touchStartPos = views.globalMouseXY
        }

        fun getPos(): Point {
            println("konakuoma" + touchStartPos)
            return touchStartPos
        }
        touchPad.onDown outer@{
                setPos()
                //setEPos()
        }
        touchPad.onMouseDrag {
            if (views.input.mouseButtonPressed(MouseButton.LEFT) && !player.jumping) {
                player.jumping = true
                facingRight = true
                launch { player.idle_right() }
                //var pos = getPos()
                // Calculate length of mouse drag
                var dragLength = getEPos().distanceTo(getPos())
                print(dragLength)
                println("kotner")
                if (dragLength.isAlmostZero()) {
                    println("drag it")
                //return@onMouseDrag
                }
                // Adjust jumpPower and jumpDistance based on drag length
                player.jumpForce = 500 + (dragLength / 2.0).coerceAtMost(750.0)
                player.jumpDistance = 100 + (dragLength / 4.0).coerceAtMost(200.0)
                println("Jump Power: ${player.jumpForce}, Jump Distance: ${player.jumpDistance}")
                jumped = true
            }
        }

            /*touchPad.onUp {
                touchEndPos = views.globalMouseXY
            }*/

        touchPad.onUpOutside {
            if (!player.jumping && jumped) {
                player.jumping = true
                velocityY = -player.jumpForce
            }
            jumped = false
        }

        touchPad2.onMouseDrag {
            if (views.input.mouseButtonPressed(MouseButton.LEFT) && !player.jumping) {
                player.jumping = true
                facingRight = false
                launch { player.idle_left() }
                //var pos = getPos()
                // Calculate length of mouse drag
                var dragLength = getEPos().distanceTo(getPos())
                print(dragLength)
                println("kotner")
                if (dragLength.isAlmostZero()) {
                    println("drag it")
                    //return@onMouseDrag
                }
                // Adjust jumpPower and jumpDistance based on drag length
                player.jumpForce = 500 + (dragLength / 2.0).coerceAtMost(750.0)
                player.jumpDistance = 100 + (dragLength / 4.0).coerceAtMost(200.0)
                println("Jump Power: ${player.jumpForce}, Jump Distance: ${player.jumpDistance}")
                jumped = true
            }
        }
        touchPad2.onUpOutside {
            if (!player.jumping && jumped) {
                player.jumping = true
                velocityY = -player.jumpForce
            }
            jumped = false
        }

        /*touchPad.onClick {
            player.jumping = true
            facingRight = true
            player.jumpForce = 500.0
            player.jumpDistance = 100.0
            velocityY = -player.jumpForce
        }*/
        //views.input.touch.isEnd

        /*touchPad2.onClick {
            player.jumping = true
            facingRight = false
            player.jumpForce = 500.0
            player.jumpDistance = 100.0
            velocityY = -player.jumpForce
        }*/

        if (views.input.keys.justPressed(Key.RIGHT) && !player.jumping) {
            player.jumping = true
            launch { player.idle_right() }
            facingRight = false
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
            //println("force:"+player.jumpForce)
            //println("distance:"+player.jumpDistance)
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
            launch { player.idle_left() }
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
            //println("force:"+player.jumpForce)
            //println("distance:"+player.jumpDistance)
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

    private fun checkCollisions() {
        val playerTop = player.y
        val playerBottom = player.y + player.height

        isOnGround = player.collidesWith(level.groundHitbox)
        if (isOnGround) {
            player.y = level.groundHitbox.y - player.height
            player.jumping = false // reset jumping flag
            isOnPlatform = false
        }
        for (hitbox in level.platformHitboxes) {
            val hitboxBottom = hitbox.y + hitbox.height
            val hitboxTop = hitbox.y
            isOnPlatform = hitbox.collidesWith(player) && playerBottom > hitboxTop

            if (hitbox.collidesWith(player) && playerBottom > hitboxTop) {
                player.y = hitbox.y - player.height
                //println(player.y)
                player.jumping = false
                isOnGround = true
            }
            if (hitbox.collidesWith(player) && playerTop < hitboxBottom && playerBottom > hitboxBottom) {
                player.y = hitbox.y + hitbox.height
                velocityY = -velocityY / 2
            }
        }
        for (enemyhb in enemy.enemyHitboxes)
        if (player.collidesWith(enemyhb)) {
            if (!playerHit) {
                health.removeHeart()
                playerHit = true
                player.loseHealth()
                launch {
                    delay(1.seconds)
                    playerHit = false
                }
            }
            if (player.lives == 0) {
                launch {
                    stop()
                }
            }
        }
        if (player.y > 1400) { // if player is out of bounds reset player position
            player.position(120, 1370)
        }
        if (player.collidesWith(level.rightwallHitbox)) {
            player.x = level.rightwallHitbox.x - player.width
        }
        if (player.collidesWith(level.leftwallHitbox)) {
            player.x = level.leftwallHitbox.x + player.width * 2
        }
        if (player.collidesWith(coin)) {
            coin.checkCollisions(player)

        }
        for (goal in level.goalHitboxes) {
            if (player.collidesWith(goal) && x == 0) {
                x = 1
                launch {
                    levelchanger(0)
                }
            }
        }
    }
    override suspend fun sceneBeforeLeaving() {
        sceneContainer.tween(tune::volume[0.0], time = .4.seconds)
        tune.stop()
        super.sceneBeforeLeaving()
    }
}

