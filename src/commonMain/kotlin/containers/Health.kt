package containers

import com.soywiz.korge.view.*
import loadImage

class Health: Container() {
    private val heartWidth = 30
    private var gap = 40
    private val heartImages = mutableListOf<Image>()

    suspend fun createHearts() {
        for (i in 0..2) {
            val heart = loadImage("miscellaneous/heart.png")
            val heartImage = image(heart) {
                position(gap + i * heartWidth, 440)
            }
            gap += 5
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

