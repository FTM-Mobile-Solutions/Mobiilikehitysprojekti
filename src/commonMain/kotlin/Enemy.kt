import com.soywiz.klock.*
import com.soywiz.korge.view.Circle
import com.soywiz.korim.color.*
import kotlin.random.*

class Enemy(radius: Double, color: RGBA) : Circle(radius, color) {

    var Espeed = 0
    var player: Circle? = null

    init {
        // Set a random initial position for the enemy
        x = 800.0 // The initial x position of the enemy
        //y = Random.nextDouble(0.0, 600.0) // The initial y position of the enemy
        y = 256.0
    }
    fun update(dt: TimeSpan) {
        // Move the enemy towards the left side of the screen
        x -= Espeed * dt.seconds

        // Check if the enemy has reached the left edge of the screen
        if (x < 0.0) {
            // Reset the enemy's position to a random y position
            x = 800.0
            y = 256.0
        }

    }
}
