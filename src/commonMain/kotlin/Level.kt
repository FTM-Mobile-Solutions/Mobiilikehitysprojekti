import com.soywiz.klock.*
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.tween.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*

class Level : Container() {
    enum class State {
        LEVEL_INIT,
        LEVEL_1,
        LEVEL_2,
        LEVEL_3
    }

    private lateinit var bg: Bitmap
    private lateinit var ground: Bitmap
    private lateinit var leftwall: Bitmap
    private lateinit var rightwall: Bitmap
    private lateinit var platform: Bitmap
    private lateinit var platform_small: Bitmap
    private lateinit var goal: Bitmap
     lateinit var goalHitbox: SolidRect
     lateinit var groundHitbox: SolidRect
     lateinit var leftwallHitbox: SolidRect
     lateinit var rightwallHitbox: SolidRect
    val platformHitboxes = mutableListOf<SolidRect>()
    val goalHitboxes = mutableListOf<SolidRect>()
    private lateinit var enemy: Enemy

    lateinit var state: State
    suspend fun levelinit() {
        state = State.LEVEL_INIT

        enemy = Enemy()

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

        state = State.LEVEL_1
    }

    fun leveldestroyer() {
        platformHitboxes.clear()
        goalHitboxes.clear()
    }

    suspend fun level1() {
        state = State.LEVEL_1
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

    suspend fun level2() {
        state = State.LEVEL_2
        //platformit kentän pohjalta ylöspäin
        createplatform(200, 1300)
        createplatform(75, 1200)
        createplatform(100, 1100)
//        createplatform_small(200, 1000)
//        createplatform_small(75, 800)
//        createplatform_small(40, 660)
//        createplatform_small(200, 650)
//        createplatform_small(200, 650)
//        createplatform_small(40, 500)
//        createplatform(200, 400)
        val addGoal = platformHitboxes.last()
        val goalX = addGoal.x + 16
        val goalY = addGoal.y - 96
        creategoal(goalX, goalY)
    }
    suspend fun level3() {
        state = State.LEVEL_3
        //platformit kentän pohjalta ylöspäin
        createplatform(225, 1325)
//        createplatform(75, 1200)
//        createplatform(100, 1100)
//        createplatform_small(200, 1000)
//        createplatform_small(75, 800)
//        createplatform_small(40, 660)
//        createplatform_small(200, 650)
//        createplatform_small(200, 650)
//        createplatform_small(40, 500)
//        createplatform(200, 400)
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
        goalHitbox = solidRect(width = goal.width, height = goal.height) {
            alpha = 0.0
            position(gx, gy)
        }
        goalHitboxes.add(goalHitbox)
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



