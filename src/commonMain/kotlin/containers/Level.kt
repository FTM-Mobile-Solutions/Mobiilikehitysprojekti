package containers

import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.*
import com.soywiz.korim.color.*
import loadImage

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
    private var color = Colors.GHOSTWHITE
     private lateinit var goalHitbox: SolidRect
     lateinit var groundHitbox: SolidRect
     lateinit var leftwallHitbox: SolidRect
     lateinit var rightwallHitbox: SolidRect
    val platformHitboxestop = mutableListOf<SolidRect>()
    val platformHitboxesbot = mutableListOf<SolidRect>()
    val goalHitboxes = mutableListOf<SolidRect>()
    private lateinit var enemy: Enemy

    lateinit var state: State
    suspend fun levelinit() {
        state = State.LEVEL_INIT

        enemy = Enemy()

        bg = loadImage("tiles/bg.png")
        val bgimage = image(bg) {
            tint = getColor()
            smoothing = false
            position(0, 736)
        }
        val bgimage_cont = image(bg) {
            tint = getColor()
            smoothing = false
            position(0, 0)
        }
        ground = loadImage("tiles/floor64.png")
        val groundimage = image(ground) {
            tint = getColor()
            smoothing = false
            position(0,1408)
        }
        val groundimagecont = image(ground) {
            tint = getColor()
            smoothing = false
            position(0,1472)
        }
        groundHitbox = solidRect(width = ground.width, height = ground.height) {
            alpha = 0.0
            position(0, 1408)
        }
        rightwall = loadImage("tiles/wall64.png")
        val rightwallimage = image(rightwall) {
            tint = getColor()
            smoothing = false
            position(332,576)
        }
        val rightwallimagecont = image(rightwall) {
            tint = getColor()
            smoothing = false
            position(332, 0)
        }
        leftwall = loadImage("tiles/wall64.png")
        val leftwallimage = image(rightwall) {
            tint = getColor()
            smoothing = false
            position(-32,576)
        }
        val leftwallimagecont = image(rightwall) {
            tint = getColor()
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
        platformHitboxestop.clear()
        platformHitboxesbot.clear()
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
        val addGoal = platformHitboxestop.last()
        val goalX = addGoal.x + 16
        val goalY = addGoal.y - 96
        creategoal(goalX, goalY)
        //createstart(90.0, 1315.0)
    }

    suspend fun level2() {
        state = State.LEVEL_2
        //platformit kentän pohjalta ylöspäin
        createplatform(200, 1300)
        createplatform_small(75, 1170)
        createplatform_small(200, 970)
        createplatform_small(40, 870)
        createplatform_small(125, 750)
        createplatform_small(40, 600)
        createplatform_small(235, 500)
        createplatform_small(135, 400)
        createplatform(40, 365)
        val addGoal = platformHitboxestop.last()
        val goalX = addGoal.x + 16
        val goalY = addGoal.y - 96
        creategoal(goalX, goalY)
        //createstart(90.0, 1315.0)
    }
    suspend fun level3() {
        state = State.LEVEL_3
        //platformit kentän pohjalta ylöspäin
        createplatform_small(135, 1250)
        createplatform_small(75, 1050)
        createplatform_small(200, 925)
        createplatform_small(40, 850)
        createplatform_small(40, 650)
        createplatform_small(235, 550)
        createplatform_small(40, 400)
        createplatform(150, 300)
        createplatform(200, 100)
        val addGoal = platformHitboxestop.last()
        val goalX = addGoal.x + 16
        val goalY = addGoal.y - 96
        creategoal(goalX, goalY)
        //createstart(90.0, 1315.0)
    }

    suspend fun creategoal(gx:Double, gy:Double) {
        goal = loadImage("tiles/goal.png")
        val goalimage = image(goal) {
            smoothing = false
            tint = getColor()
            position(gx, gy)
        }
        goalHitbox = solidRect(width = goal.width, height = goal.height) {
            alpha = 0.0
            position(gx, gy)
        }
        goalHitboxes.add(goalHitbox)
    }

    suspend fun createstart(gx:Double, gy:Double) {
        goal = loadImage("tiles/goal.png")
        val goalimage = image(goal) {
            smoothing = false
            tint = getColor()
            position(gx, gy)
        }
    }

    suspend fun createplatform(gx:Int, gy:Int) {
        platform = loadImage("tiles/platform.png")
        val platformimage = image(platform) {
            smoothing = false
            tint = getColor()
            position(gx, gy)
        }
        val platformHitboxtop = solidRect(width = platform.width, height = platform.height / 2) {
            alpha = 0.0
            position(gx, gy)
        }
        val platformHitboxbot = solidRect(width = platform.width, height = platform.height / 2) {
            tint = Colors.RED
            alpha = 0.0
            position(gx, gy + platform.height / 2)
        }
        platformHitboxestop.add(platformHitboxtop)
        platformHitboxesbot.add(platformHitboxbot)
    }

    suspend fun createplatform_small(gx:Int, gy:Int) {
        platform_small = loadImage("tiles/platform_small.png")
        val platformimage = image(platform_small) {
            tint = getColor()
            smoothing = false
            position(gx, gy)
        }
        val platformsmallHitboxtop = solidRect(width = platform_small.width, height = platform_small.height / 2) {
            alpha = 0.0
            position(gx, gy)
        }
        val platformsmallHitboxbot = solidRect(width = platform_small.width, height = platform_small.height / 2) {
            tint = Colors.BLUE
            alpha = 0.0
            position(gx, gy + platform_small.height / 2)
        }
        platformHitboxestop.add(platformsmallHitboxtop)
        platformHitboxesbot.add(platformsmallHitboxbot)
    }
    fun setColor(tint: RGBA) {
        this.color = tint
    }
    fun getColor():RGBA {
        return color
    }
}



