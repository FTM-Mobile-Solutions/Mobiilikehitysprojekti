package containers

import com.soywiz.korau.sound.*
import com.soywiz.korge.view.*
import com.soywiz.korio.file.std.*

class SceneSound : Container() {
    suspend fun navSound() {
        val navSound = resourcesVfs["sfx/navigation.wav"].readSound()
        navSound.play()
    }
}
