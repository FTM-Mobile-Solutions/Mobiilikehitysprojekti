import com.soywiz.korim.bitmap.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

suspend fun loadImage(fileName: String): Bitmap = resourcesVfs[fileName].readBitmap()

class loadImage {
    lateinit var playerImage: Bitmap

    suspend fun init() {
        playerImage = loadImage("player.png")
    }
}
