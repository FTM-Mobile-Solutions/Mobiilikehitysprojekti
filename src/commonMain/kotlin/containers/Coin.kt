package containers

import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korio.file.std.*
import loadImage

class Coin: Container()  {

     var coinCounter = 0
     private var points = 0
     private val coinBitmaps = mutableMapOf<String, Image>()
     private val coinHitboxes = mutableMapOf<String, SolidRect>()

    suspend fun level1_coins() {
        createCoin(255, 1290)
        createCoin(40, 1100)

        createCoin(40, 900)

        createCoin(290, 850)
        createCoin(290, 800)
        createCoin(290, 750)
    }

    suspend fun level2_coins() {
        createCoin(250, 1270)

        createCoin(40, 950)

        createCoin(290, 850)
        createCoin(290, 800)
        createCoin(290, 750)

        createCoin(290, 600)
        createCoin(290, 650)

        createCoin(160, 475)
    }
    suspend fun level3_coins() {
        createCoin(40, 1100)
        createCoin(290, 1100)

        createCoin(40, 900)

        createCoin(70, 750)

        createCoin(290, 600)
        createCoin(290, 650)

        createCoin(165, 475)

        createCoin(290, 200)

        createCoin(40, 100)
        createCoin(40, 150)
    }

    /*suspend fun load(coordinates: Array<Pair<Int, Int>>) {
        println(coordinates.size)

        for ((gx, gy) in coordinates) {
            createCoin(gx, gy)
        }
        val coin = Container.containers.Coin()
        val coordinates = arrayOf(
            Pair(100, 200),
            Pair(500, 300),
            Pair(800, 600)
        )
        coin.load(coordinates)

    }*/


    suspend fun createCoin(gx: Int, gy: Int) {
        val coin = loadImage("miscellaneous/coin.png")
        val coinBitmap = image(coin) {
            smoothing = false
            position(gx, gy)
        }
        val coinHitbox = solidRect(width = coin.width, height = coin.height) {
            alpha = 0.0
            position(gx, gy)
        }
        val coinName = "coin_${coinCounter++}"
        coinBitmaps[coinName] = coinBitmap
        coinHitboxes[coinName] = coinHitbox
    }

    suspend fun checkCollisions(player: View) {
        val coinSound = resourcesVfs["sfx/coin.wav"].readSound()
        for ((coinName, coinHitbox) in coinHitboxes) {

            if (player.collidesWith(coinHitbox)) {
                coinSound.play()
                val coinBitmap = coinBitmaps[coinName]
                coinBitmap?.removeFromParent()
                points++
                coinCounter--

                coinHitbox.removeFromParent()

                coinBitmaps.remove(coinName)
                coinHitboxes.remove(coinName)
                break
            }
        }
    }

}

