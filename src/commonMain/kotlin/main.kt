
import com.soywiz.korge.*
import com.soywiz.korge.scene.*
import com.soywiz.korim.color.*
import com.soywiz.korinject.*
import com.soywiz.korma.geom.*
import kotlin.reflect.*


suspend fun main() = Korge(Korge.Config(module = MainModule))

object MainModule : Module() {
    override val mainScene: KClass<out Scene>
        get() = FinalScene::class
    override val title: String
        get() = "Konapeli"
    override val size: SizeInt
        get() = SizeInt(360, 1600)
    override val scaleMode: ScaleMode
        get() = ScaleMode.COVER
    override val windowSize: SizeInt
        get() = SizeInt(360, 800)
    override val bgcolor
        get() = Colors.BLACK

    override suspend fun AsyncInjector.configure() {
        mapPrototype { GameScene() }
        mapPrototype { MainScene() }
        mapPrototype { SplashScreen() }
        mapPrototype { GameOver() }
        mapPrototype { FinalScene() }
    }
}

