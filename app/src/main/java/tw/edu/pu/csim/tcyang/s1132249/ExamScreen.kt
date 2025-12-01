package tw.edu.pu.csim.tcyang.s1132249

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import android.graphics.RectF // 導入 Android 圖形矩形類別

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {

    // --- 1. 尺寸和高度計算 ---
    val density = LocalDensity.current
    val imageSizePxDp = 300
    // 將 300px 轉換為 Dp
    val imageSizeDp: Dp = with(density) { imageSizePxDp.toDp() }

    // 掉落圖示的像素尺寸 (300px)
    val iconSizePx = with(density) { imageSizeDp.toPx() }

    // 獲取屏幕的 DP 高度和像素高度/寬度
    val view = LocalView.current
    val screenHeightPxFloat = view.height.toFloat()
    val screenWidthPxFloat = viewModel.screenWidthPx.toFloat()
    val screenHeightDp: Dp = LocalConfiguration.current.screenHeightDp.dp

    // 每 0.1s 掉落 20px (轉換為像素)
    val dropDistancePx = with(density) { 20.dp.toPx() }

    // ==========================================================
    // C. 角色圖示的固定位置 (像素坐標 RectF)
    // ==========================================================

    val screenHalfHeightPx = screenHeightPxFloat / 2f
    val screenTotalHeightPx = screenHeightPxFloat
    val screenTotalWidthPx = screenWidthPxFloat

    // 角色圖示的矩形範圍 (左上角 x, 左上角 y, 右下角 x, 右下角 y)
    val role0Rect = RectF(0f, screenHalfHeightPx - iconSizePx, iconSizePx, screenHalfHeightPx) // 嬰兒, 底部貼齊 1/2
    val role1Rect = RectF(screenTotalWidthPx - iconSizePx, screenHalfHeightPx - iconSizePx, screenTotalWidthPx, screenHalfHeightPx) // 兒童, 底部貼齊 1/2
    val role2Rect = RectF(0f, screenTotalHeightPx - iconSizePx, iconSizePx, screenTotalHeightPx) // 成人, 底部貼齊底部
    val role3Rect = RectF(screenTotalWidthPx - iconSizePx, screenTotalHeightPx - iconSizePx, screenTotalWidthPx, screenTotalHeightPx) // 一般民眾, 底部貼齊底部

    val roleRects = mapOf(
        "嬰兒圖示" to role0Rect,
        "兒童圖示" to role1Rect,
        "成人圖示" to role2Rect,
        "一般民眾圖示" to role3Rect
    )

    // ==========================================================
    // D. 动画/碰撞逻辑 (第四題/第五題)
    // ==========================================================
    LaunchedEffect(viewModel.isFalling) {
        while (viewModel.isFalling) {
            delay(100)

            val proposedYPx = viewModel.fallingIconY + dropDistancePx
            var endState = false
            var message = ""

            // 1. 檢查是否觸底 (下一位置底部 > 屏幕底部)
            if (proposedYPx + iconSizePx > screenTotalHeightPx) {
                endState = true
                message = "(掉到最下方)"
            }

            // 2. 檢查是否碰撞角色圖示 (使用下一位置的矩形)
            val fallingIconRect = RectF(
                viewModel.fallingIconX - iconSizePx / 2f, // 左
                proposedYPx, // 上
                viewModel.fallingIconX + iconSizePx / 2f, // 右
                proposedYPx + iconSizePx // 下
            )

            var collidedRole: String? = null
            if (!endState) { // 只有在沒有觸底的情況下才檢查碰撞
                for ((roleName, roleRect) in roleRects) {
                    if (fallingIconRect.intersect(roleRect)) {
                        collidedRole = roleName
                        endState = true
                        message = "(碰撞${collidedRole})"
                        break
                    }
                }
            }

            if (endState) {
                // 碰撞或觸底：顯示訊息並停止動畫
                viewModel.handleEndState(message)

                // 延遲一段時間讓使用者看到結果
                delay(1000)

                // 重新生成新的掉落圖示，繼續遊戲
                viewModel.generateNewFallingIcon(viewModel.screenWidthPx)

            } else {
                // 3. 沒有碰撞也沒有觸底，繼續掉落
                viewModel.updateIconYPosition(proposedYPx)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(16.dp)
    ) {

        // A. 中間的文字內容 (修正：分數後顯示碰撞訊息)
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(id = R.drawable.happy),
                contentDescription = "Happy Icon",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(10.dp)) // 間距 10dp

            Text(text = "瑪利亞基金會服務大考驗")
            Text(text = "作者:資管二B 羅婉薰") // <-- 請修改成您的系級與姓名

            Spacer(modifier = Modifier.height(10.dp))

            // 螢幕尺寸顯示
            Text(text = "螢幕大小: ${viewModel.screenWidthPx.toFloat()} * ${viewModel.screenHeightPx.toFloat()} px")

            Spacer(modifier = Modifier.height(10.dp))

            // 顯示分數和碰撞訊息
            Text(text = "成績: ${viewModel.score} 分 ${viewModel.collisionMessage}")
        }

        // B. 四個角色圖示 (第三題，位置不變)

        // 1. 嬰兒 (role0)：左邊貼齊，下方貼齊屏幕高度 1/2
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "Baby",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.TopStart)
                .offset(y = screenHeightDp * 0.5f - imageSizeDp)
        )

        // 2. 兒童 (role1)：右邊貼齊，下方貼齊屏幕高度 1/2
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "Child",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.TopEnd)
                .offset(y = screenHeightDp * 0.5f - imageSizeDp)
        )

        // 3. 成人 (role2)：左邊貼齊，下方貼齊屏幕底部
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "Adult",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.BottomStart)
        )

        // 4. 一般民眾 (role3)：右邊貼齊，下方貼齊屏幕底部
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "Citizen",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.BottomEnd)
        )

        // E. 掉落的服务图示 (第四题/第五題)
        if (viewModel.isFalling || viewModel.collisionMessage.isNotEmpty()) {
            Image(
                painter = painterResource(id = viewModel.currentIconResId),
                contentDescription = "Falling Icon",
                modifier = Modifier
                    .size(imageSizeDp)
                    // 1. 設置位置
                    .offset {
                        IntOffset(
                            // 修正 X 座標：中心點 - 半徑 = 視覺的左上角 X
                            x = (viewModel.fallingIconX - iconSizePx / 2f).roundToInt(),
                            y = viewModel.fallingIconY.roundToInt()
                        )
                    }
                    // 2. 水平拖拽
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            if (viewModel.isFalling) {
                                val proposedX = viewModel.fallingIconX + dragAmount.x

                                // 邊界檢查
                                val iconHalfWidth = iconSizePx / 2f
                                val minX = iconHalfWidth
                                val maxX = screenWidthPxFloat - iconHalfWidth

                                viewModel.updateIconXPosition(proposedX.coerceIn(minX, maxX))
                            }
                        }
                    }
            )
        }
    }
}