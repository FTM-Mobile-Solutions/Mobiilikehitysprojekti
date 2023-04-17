import com.soywiz.korge.view.*

class Health: Container() {
    private val heartWidth = 30
    private val heartImages = mutableListOf<Image>()

    suspend fun createHearts() {
        for (i in 0..2) {
            val heart = loadImage("heart.png")
            val heartImage = image(heart) {
                position(30 + i * heartWidth, 1200)
            }
            heartImages.add(heartImage)
        }
    }
    fun removeHeart() {
        if (heartImages.isNotEmpty()) {
            val lastHeart = heartImages.last()
            removeChild(lastHeart)
            heartImages.remove(lastHeart)
        }
        if (heartImages.isEmpty()) {
            return
        }
    }
}

