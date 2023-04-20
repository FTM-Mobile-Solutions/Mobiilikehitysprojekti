import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

suspend fun loadImage(fileName: String): Bitmap = resourcesVfs[fileName].readBitmap()

class loadImage {
    lateinit var playerImage: Bitmap
    lateinit var playerlImage: Bitmap
    lateinit var playerleftImage: Bitmap
    lateinit var playerrightImage: Bitmap
    lateinit var playerjumpleftImage: Bitmap
    lateinit var playerjumprightImage: Bitmap
    lateinit var enemyImage: Bitmap
    lateinit var groundBitmap: Bitmap
    lateinit var bgImage: Bitmap
    lateinit var goImage: Bitmap
    lateinit var platformBitmap: Bitmap
    lateinit var floor: Bitmap
    lateinit var wall: Bitmap
    lateinit var play: Bitmap
    private lateinit var goal: Bitmap

    suspend fun init() {
        playerImage = loadImage("entities/player.png")
        playerlImage = loadImage("entities/playerl.png")
        playerleftImage = loadImage("player_left.png")
        playerrightImage = loadImage("player_right.png")
        playerjumpleftImage = loadImage("player_jumping_left.png")
        playerjumprightImage = loadImage("player_jumping_right.png")
        enemyImage = loadImage("entities/enemy.png")
        bgImage = loadImage("testibg.png")
        groundBitmap = loadImage("tiles/bg.png")
        goImage = loadImage("miscellaneous/gameover.png")
        platformBitmap = loadImage("tiles/platform.png")
//        floor = loadImage("tiles/floor_green.png")
        floor = loadImage("tiles/floor64.png")
        wall = loadImage("tiles/wall64.png")
        play = loadImage("miscellaneous/startbutton.png")
        play = loadImage("tiles/goal.png")
//        wall = loadImage("tiles/wall_green.png")
    }
}
