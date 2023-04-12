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
    lateinit var platform_small: Bitmap
    lateinit var goal: Bitmap
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
            position(0, 736)
        }
        val bgimage_cont = image(bg) {
            tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(0, 0)
        }
        ground = loadImage("tiles/floor64.png")
        val groundimage = image(ground) {
//            tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(0,1408)
        }
        val groundimagecont = image(ground) {
//            tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(0,1472)
        }
        groundHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 1408)
        }
        rightwall = loadImage("tiles/wall64.png")
        val rightwallimage = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(332,576)
        }
        val rightwallimagecont = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(332, 0)
        }
        leftwall = loadImage("tiles/wall64.png")
        val leftwallimage = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(-32,576)
        }
        val leftwallimagecont = image(rightwall) {
            //tint = Colors.LIGHTSLATEGREY
            smoothing = false
            position(-32,0)
        }
        leftwallHitbox = solidRect(width = leftwall.width, height = leftwall.height * 2) {
            alpha = 0.0
            position(-32, -192)
        }
        rightwallHitbox = solidRect(width = rightwall.width, height = rightwall.height * 2) {
            alpha = 0.0
            position(332, -192)
        }

        //platformit kentän pohjalta ylöspäin
        createplatform_small(225, 1325)
        createplatform_small(75, 1275)
        createplatform(100, 1100)
        createplatform_small(200, 1000)
        createplatform_small(75, 800)
        createplatform_small(40, 660)
        createplatform_small(200, 650)
        createplatform_small(200, 650)
        createplatform_small(40, 500)
        createplatform(200, 400)
        val addGoal = platformHitboxes.last()
        val goalX = addGoal.x + 16
        val goalY = addGoal.y - 96
        creategoal(goalX, goalY)
    }

    suspend fun creategoal(gx:Double, gy:Double) {
        goal = loadImage("goal.png")
        val goalimage = image(goal) {
            smoothing = false
            position(gx, gy)
        }
        val heartWidth = 30 // replace with actual width of heart image
        val heartImages = mutableListOf<Image>()
        for (i in 0..2) {
            val heart = loadImage("heart.png")
            val heartImage = image(heart) {
                smoothing = false
                position(20 + i * heartWidth, 1408)
            }
            heartImages.add(heartImage)
        }
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

    suspend fun createplatform_small(gx:Int, gy:Int) {
        platform_small = loadImage("tiles/platform_small.png")
        val platformimage = image(platform_small) {
            smoothing = false
            position(gx, gy)
        }
        val platform_smallHitbox = solidRect(width = platform_small.width, height = platform_small.height) {
            alpha = 0.0
            position(gx, gy)
        }
        platformHitboxes.add(platform_smallHitbox)
    }
}



