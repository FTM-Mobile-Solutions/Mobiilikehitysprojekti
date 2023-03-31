import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*

class Level : Container() {
    enum class State {
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

     lateinit var bg: Bitmap
     lateinit var ground: Bitmap
     lateinit var leftwall: Bitmap
     lateinit var rightwall: Bitmap
     lateinit var platform: Bitmap
     lateinit var groundHitbox: SolidRect
     lateinit var leftwallHitbox: SolidRect
     lateinit var rightwallHitbox: SolidRect
     val platformHitboxes = mutableListOf<SolidRect>()

    lateinit var state: State

    suspend fun level1() {
        state = State.LEVEL_1
        bg = loadImage("tiles/bg.png")
        val bgimage = image(bg) {
            tint = Colors.LIGHTSLATEGREY
            smoothing = false
        }
        ground = loadImage("tiles/floor64.png")
        val groundimage = image(ground) {
//            tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(0,736)
        }
        groundHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 736)
        }
        leftwallHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 0)
        }
        rightwall = loadImage("tiles/wall64.png")
        val rightwallimage = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(332,-96)
        }
        leftwall = loadImage("tiles/wall64.png")
        val leftwallimage = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(-32,-96)
        }
        rightwallHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(344, 0)
        }


        createplatform(50, 650)
        createplatform(200, 550)
        createplatform(50, 450)
        createplatform(200, 350)
    }

    suspend fun createplatform(gx:Int, gy:Int) {
        platform = loadImage("tiles/platform.png")
        val platformimage = image(platform) {
            smoothing = false
            position(gx, gy)
        }
        val platformHitbox = solidRect(width = platform.width, height = platform.height) {
            alpha = 0.0
            position(gx, gy)
        }
        platformHitboxes.add(platformHitbox)
    }
}
