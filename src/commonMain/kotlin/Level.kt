import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*

class Level : Container() {
    enum class State {
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

     lateinit var bg: Bitmap
     lateinit var ground: Bitmap
     lateinit var platform: Bitmap
     lateinit var groundHitbox: SolidRect
     val platformHitboxes = mutableListOf<SolidRect>()

    lateinit var state: State

    suspend fun level1() {
        state = State.LEVEL_1
        bg = loadImage("testibg.png")
        val bgimage = image(bg) {
            smoothing = false
        }
        ground = loadImage("platformtest.png")
        val groundimage = image(ground) {
            smoothing = false
            position(0,500)
        }
        groundHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 500)
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
