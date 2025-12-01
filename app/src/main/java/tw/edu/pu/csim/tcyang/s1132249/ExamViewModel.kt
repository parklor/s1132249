package tw.edu.pu.csim.tcyang.s1132249

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random
import tw.edu.pu.csim.tcyang.s1132249.R

private val serviceIcons = listOf(
    R.drawable.service0,
    R.drawable.service1,
    R.drawable.service2,
    R.drawable.service3,
)

class ExamViewModel : ViewModel() {

    // --- 狀態變數 (第一題至第四題) ---

    var screenWidthPx by mutableStateOf(0)
        private set

    var screenHeightPx by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    var fallingIconY by mutableStateOf(0f)
        private set

    var fallingIconX by mutableStateOf(0f)
        private set

    var currentIconResId by mutableStateOf(serviceIcons.first())
        private set

    var isFalling by mutableStateOf(false)
        private set

    // --- 第五題：碰撞訊息狀態 ---
    var collisionMessage by mutableStateOf("")
        private set


    // --- 函數 (第一題至第五題) ---

    fun updateScreenDimensions(width: Int, height: Int) {
        screenWidthPx = width
        screenHeightPx = height
    }
    fun generateNewFallingIcon(screenWidthPx: Int) {
        if (serviceIcons.isEmpty()) return

        currentIconResId = serviceIcons.random()
        fallingIconX = screenWidthPx / 2f
        fallingIconY = 0f

        collisionMessage = "" // 清除舊的碰撞訊息
        isFalling = true
    }

    fun updateIconYPosition(newY: Float) {
        fallingIconY = newY
    }

    fun updateIconXPosition(newX: Float) {
        fallingIconX = newX
    }

    fun handleEndState(message: String) {
        isFalling = false // 停止動畫
        collisionMessage = message // 顯示訊息
    }
}