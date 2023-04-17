import com.soywiz.korge.view.*

class Health: Container() {
    suspend fun createhearts() {
        val heartWidth = 30
        val heartImages = mutableListOf<Image>()

        for (i in 0..2) {
            val heart = loadImage("heart.png")
            val heartImage = image(heart) {
                position(40 + i * heartWidth, 350)
            }
            heartImages.add(heartImage)
        }
    }
}
