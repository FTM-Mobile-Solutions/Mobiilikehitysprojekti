import com.soywiz.korau.sound.*
import com.soywiz.korge.Korge
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korev.Key
import com.soywiz.korge.lipsync.*
import com.soywiz.korge.view.Circle
import com.soywiz.korim.format.*
import com.soywiz.korio.async.*
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.*
import java.lang.StrictMath.random
import kotlin.math.*



suspend fun main() = Korge(width = 512, height = 512, bgcolor = Colors["#2b2b2b"]) {
    val bG = image(resourcesVfs["kornertausta.png"].readBitmap()){
    }
    val playerVoice = resourcesVfs["konaa.wav"].readSound()
    // camera adjust
    val camera = fixedSizeContainer(width, height)
    addChild(camera)

    // Create a solid rectangle
    val solidRect = solidRect(width = 10000000000000.0, height = 40.0, color = Colors.GREEN)
    val solidBase = solidRect(width = 10000000000000.0, height = 200.0, color = Colors.SANDYBROWN)
    val solidObstacle = solidRect(width = 100.0, height = 10.0, color = Colors.GREEN)
    // Position the solid objects
    solidRect.position(0.0, 400.0)
    solidBase.position(0.0, 440.0)
    solidObstacle.position(500, 300)

    val player = circle(32.0, Colors.BLUE) {
        xy(256, 256)
    }

    // Create enemy sprites
    val enemies = arrayListOf<Circle>()
    repeat(10) {
        val enemy = circle(16.0, Colors.RED) {
            xy(500, 256)
        }
        enemies.add(enemy as Circle)
    }

    // Create score text
    val scoreText = text("Score: 0", textSize = 24.0, Colors.WHITE) {
        xy(16.0, 16.0)
    }

    // Initialize score variable
    var score = 0

    camera.addChild(solidRect)
    camera.addChild(solidBase)
    camera.addChild(player)
    camera.addChild(solidObstacle)


    val gravity = 250.0

    var velocityY = 0.0

    var konaPlayed = false

    fun konaSound(){
        playerVoice.volume = 0.1 // sets the volume to 50%
        if(!konaPlayed) {
            launch {
                playerVoice.play()
                konaPlayed = true
            }
        }else
            konaPlayed = false

    }

    addUpdater { dt ->
        velocityY += gravity * dt.seconds
        player.y += velocityY * dt.seconds
        if (keys[Key.LEFT]) player.x -= 256 * dt.milliseconds / 1000.0
        if (keys[Key.LEFT]) {
            // Check if the player is at the left edge of the screen
            if (player.x > 0.0) {
                player.x -= 256 * dt.milliseconds / 1000.0
            } else {
                // Player is at the left edge of the screen, so stop moving left
                player.x = 0.0
            }
        }
        if (keys[Key.RIGHT]) player.x += 256 * dt.milliseconds / 1000.0
        if (keys[Key.SPACE]) player.y -= 256 * dt.milliseconds / 1000.0
        if (keys[Key.SPACE]){
            konaSound()
        }
        // Enemy movement and collision detection
        enemies.forEach { enemy ->
            // Move enemy towards player
            val dx = player.x - enemy.x
            //val dy = player.y - enemy.y
            //val angle = atan2(dy, dx)
            //enemy.x += cos(angle) * 128 * dt.milliseconds / 1000.0
            enemy.x -= 128 * dt.milliseconds / 1000.0
            //enemy.y += sin(angle) * 128 * dt.milliseconds / 1000.0

            // Check for collision with player
            if (player.distanceTo(enemy) < 32.0) {
                // Decrement score and reset player position
                score--
                scoreText.text = "Score: $score"
                player.xy(256, 256)
            }
        }

        // Check for collision with enemies
        enemies.filter { player.distanceTo(it) < 32.0 }.forEach { enemy ->
            // Increment score and remove enemy
            score++
            scoreText.text = "Score: $score"
            enemy.removeFromParent()
            enemies.remove(enemy)
        }

        // Check for game over
        if (enemies.isEmpty()) {
            text("Game over!", textSize = 48.0, Colors.WHITE) {
                xy(128.0, 256.0)
            }
        }
        //if (keys[Key.DOWN]) player.y += 256 * dt.milliseconds / 1000.0
        if(player.collidesWith(solidRect)){
            player.y = solidRect.y - player.radius*2
            velocityY = 0.0
        }
        camera.x = - player.x + width / 2
        camera.y = - player.y + height / 2
        if(camera.x > 0.0){
            camera.x = 0.0
        }
        if(player.collidesWith(solidObstacle)){
            player.y = solidObstacle.y - player.radius*2
            velocityY = 0.0
        }

    }
}
