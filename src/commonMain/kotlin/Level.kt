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
//        bg = loadImage("testibg.png")
//        val bgimage = image(bg) {
//            smoothing = false
//        }
//        ground = loadImage("tiles/floor_green.png")
        ground = loadImage("tiles/floor64.png")
        val groundimage = image(ground) {
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
//        rightwall = loadImage("tiles/wall_green.png")
        rightwall = loadImage("tiles/wall64.png")
        val rightwallimage = image(rightwall) {
            smoothing = false
            position(332,-96)
        }
        leftwall = loadImage("tiles/wall64.png")
        val leftwallimage = image(rightwall) {
            smoothing = false
            position(-32,-96)
        }
        rightwallHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(344, 0)
        }


        createplatform(1000, 350)
        createplatform(700, 350)
        createplatform(400, 350)
        createplatform(100, 440)

    }

    suspend fun createplatform(gx:Int, gy:Int) {
        platform = loadImage("konaplatformtest.png")
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
