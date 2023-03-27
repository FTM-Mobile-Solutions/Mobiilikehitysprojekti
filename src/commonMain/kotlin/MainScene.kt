import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*

class MainScene : Scene() {
    private lateinit var bg: Image
    private lateinit var player: Player

    override suspend fun SContainer.sceneInit() {

        bg = image(resourcesVfs["testibg.png"].readBitmap()) {
            smoothing = false
        }

        player = Player()
        player.load()
        addChild(player)
    }
}
