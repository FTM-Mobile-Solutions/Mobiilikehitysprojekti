import com.soywiz.korge.view.*
import kotlin.random.Random

class Coin: Container()  {

     var coinCounter = 0
    var points = 0
     val coinBitmaps = mutableMapOf<String, Image>()
     val coinHitboxes = mutableMapOf<String, SolidRect>()

    suspend fun load(amount: Int) {
        val random = Random.Default
        //println(amount)

//        for (i in 1..amount) {
//            val x = random.nextInt(64, 200)
//            val y = random.nextInt(500, 1000)
            createCoin(220, 1300)
    }

    /*suspend fun load(coordinates: Array<Pair<Int, Int>>) {
        println(coordinates.size)

        for ((gx, gy) in coordinates) {
            createCoin(gx, gy)
        }
        val coin = Coin()
        val coordinates = arrayOf(
            Pair(100, 200),
            Pair(500, 300),
            Pair(800, 600)
        )
        coin.load(coordinates)

    }*/


    suspend fun createCoin(gx: Int, gy: Int) {
        val coin = loadImage("coin.png")
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

    fun checkCollisions(player: View) {
        for ((coinName, coinHitbox) in coinHitboxes) {

            if (player.collidesWith(coinHitbox)) {
                val coinBitmap = coinBitmaps[coinName]
                coinBitmap?.removeFromParent()
                points++

                coinHitbox.removeFromParent()

                coinBitmaps.remove(coinName)
                coinHitboxes.remove(coinName)
                break;

            }
        }
    }

}

