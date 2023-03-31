import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

suspend fun loadImage(fileName: String): Bitmap = resourcesVfs[fileName].readBitmap()

class loadImage {
    lateinit var playerImage: Bitmap
    lateinit var groundBitmap: Bitmap
    lateinit var bgImage: Bitmap
    lateinit var platformBitmap: Bitmap
    lateinit var floor: Bitmap
    lateinit var wall: Bitmap

    suspend fun init() {
        playerImage = loadImage("player.png")
        bgImage = loadImage("testibg.png")
        groundBitmap = loadImage("platformtest.png")
        platformBitmap = loadImage("konaplatformtest.png")
//        floor = loadImage("tiles/floor_green.png")
        floor = loadImage("tiles/floor64.png")
        wall = loadImage("tiles/wall64.png")
//        wall = loadImage("tiles/wall_green.png")
    }
}
