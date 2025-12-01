package tw.edu.pu.csim.tcyang.s1132249

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random
import tw.edu.pu.csim.tcyang.s1132249.R

// 假設您的服務圖示資源ID列表 (請根據您的實際資源名進行補充)
private val serviceIcons = listOf(
    R.drawable.service0,
    R.drawable.service1,
    R.drawable.service2,
    R.drawable.service3,
)

class ExamViewModel : ViewModel() {

    // --- 第二題：螢幕尺寸與分數狀態 ---

    // 螢幕寬度 (像素 px)
    var screenWidthPx by mutableStateOf(0)
        private set

    // 螢幕高度 (像素 px)
    var screenHeightPx by mutableStateOf(0)
        private set

    // 分數狀態 (初始為 0 分)
    var score by mutableStateOf(0)
        private set

    // 更新螢幕尺寸的方法 (在 MainActivity 中調用)
    fun updateScreenDimensions(width: Int, height: Int) {
        screenWidthPx = width
        screenHeightPx = height
    }

    // --- 第四題：隨機掉落圖示的狀態管理 ---

    // 掉落圖示的當前垂直位置 (Y 座標，像素 px)
    var fallingIconY by mutableStateOf(0f)
        private set

    // 掉落圖示的當前水平位置 (X 座標，像素 px，代表圖示的中心點)
    var fallingIconX by mutableStateOf(0f)
        private set

    // 掉落圖示的資源ID (隨機產生)
    var currentIconResId by mutableStateOf(serviceIcons.first())
        private set

    // 標記是否正在掉落 (用於控制動畫)
    var isFalling by mutableStateOf(false)
        private set

    /**
     * 初始化或重置掉落圖示。
     * 從螢幕上方、水平中間開始。
     *
     */
    fun generateNewFallingIcon(screenWidthPx: Int) {
        if (serviceIcons.isEmpty()) return

        // 1. 隨機產生一個服務圖示
        currentIconResId = serviceIcons.random()

        // 2. 初始位置：螢幕上方，水平中間
        fallingIconX = screenWidthPx / 2f
        fallingIconY = 0f // 初始Y座標在螢幕頂部

        isFalling = true
    }

    // 更新垂直位置 (由 ExamScreen 中的動畫循環呼叫)
    fun updateIconYPosition(newY: Float) {
        fallingIconY = newY
    }

    // 更新水平位置 (由 ExamScreen 中的拖曳手勢呼叫)
    fun updateIconXPosition(newX: Float) {
        fallingIconX = newX
    }

    /**
     * 當圖示觸碰螢幕下方時，重新生成並繼續掉落。
     *
     */
    fun handleTouchBottom(screenWidthPx: Int) {
        generateNewFallingIcon(screenWidthPx)
    }
}