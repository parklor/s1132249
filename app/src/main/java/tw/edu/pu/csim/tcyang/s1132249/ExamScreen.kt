package tw.edu.pu.csim.tcyang.s1132249

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures // 導入手勢
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // 導入動畫協程
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput // 導入 pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView // 導入 LocalView 獲取實際像素高度
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.roundToInt
import kotlinx.coroutines.delay // 導入協程延遲
import tw.edu.pu.csim.tcyang.s1132249.R

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {

    // --- 1. 尺寸和高度計算 (第三題 & 第四題共用) ---
    val density = LocalDensity.current
    val imageSizePxDp = 300
    // 將 300px 轉換為 Dp
    val imageSizeDp: Dp = with(density) { imageSizePxDp.toDp() }

    // 掉落圖示的像素尺寸 (300px)
    val iconSizePx = with(density) { imageSizeDp.toPx() }

    // 獲取屏幕的 DP 高度和像素高度/寬度
    val screenHeightDp: Dp = LocalConfiguration.current.screenHeightDp.dp
    val view = LocalView.current
    val screenHeightPxFloat = view.height.toFloat()
    val screenWidthPxFloat = viewModel.screenWidthPx.toFloat()


    val dropDistancePx = with(density) { 20.dp.toPx() }

    LaunchedEffect(viewModel.isFalling) {
        while (viewModel.isFalling) {
            delay(100) // 延迟 0.1 秒

            val newYPx = viewModel.fallingIconY + dropDistancePx

            // 觸底檢測：(当前Y + 图示高度 > 屏幕实际像素高度)
            if (newYPx + iconSizePx > screenHeightPxFloat) {
                viewModel.handleTouchBottom(viewModel.screenWidthPx) // 觸底，重置
            } else {
                viewModel.updateIconYPosition(newYPx) // 繼續掉落
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize() // 確保 Box 充滿整個螢幕
            .background(Color.Yellow) // 黃色背景
            .padding(16.dp) // 外層填充
    ) {

        // A. 中間的文字內容 (保持居中不變)
        Column(
            modifier = Modifier
                .align(Alignment.Center), // 確保在 Box 中水平和垂直居中，位置穩定
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 頂部的圓形圖片 (Happy Icon)
            Image(
                painter = painterResource(id = R.drawable.happy),
                contentDescription = "Happy Icon",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(10.dp)) // 間距高度 10dp

            Text(text = "瑪利亞基金會服務大考驗")
            Text(text = "作者:資管二B 羅婉薰") // <-- 請修改成您的系級與姓名

            Spacer(modifier = Modifier.height(10.dp)) // 間距高度 10dp

            // 螢幕尺寸顯示 (讀取螢幕寬度與高度 px)
            Text(text = "螢幕大小: ${viewModel.screenWidthPx.toFloat()} * ${viewModel.screenHeightPx.toFloat()} px")

            Spacer(modifier = Modifier.height(10.dp)) // 間距高度 10dp

            Text(text = "成績: ${viewModel.score} 分")
        }

        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "Baby",
            modifier = Modifier
                .size(imageSizeDp) // 尺寸 300px
                .align(Alignment.TopStart) // 左邊貼齊
                // Offset：Y 偏移 = 屏幕高度一半 - 圖片高度 (確保底部貼齊 1/2 線)
                .offset(y = screenHeightDp * 0.5f - imageSizeDp)
        )

        // 2. 兒童 (role1)：右邊貼齊，下方貼齊屏幕高度 1/2
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "Child",
            modifier = Modifier
                .size(imageSizeDp) // 尺寸 300px
                .align(Alignment.TopEnd) // 右邊貼齊
                // Offset：Y 偏移 = 屏幕高度一半 - 圖片高度 (確保底部貼齊 1/2 線)
                .offset(y = screenHeightDp * 0.5f - imageSizeDp)
        )

        // 3. 成人 (role2)：左邊貼齊，下方貼齊屏幕底部
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "Adult",
            modifier = Modifier
                .size(imageSizeDp) // 尺寸 300px
                .align(Alignment.BottomStart) // 左下角對齊
        )

        // 4. 一般民眾 (role3)：右邊貼齊，下方貼齊屏幕底部
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "Citizen",
            modifier = Modifier
                .size(imageSizeDp) // 尺寸 300px
                .align(Alignment.BottomEnd) // 右下角對齊
        )

        if (viewModel.isFalling) {
            Image(
                painter = painterResource(id = viewModel.currentIconResId),
                contentDescription = "Falling Icon",
                modifier = Modifier
                    .size(imageSizeDp) // 尺寸 300px
                    // 1. 設置位置 (使用 x/y 狀態)
                    .offset {
                        IntOffset(
                            // 修正 X 座標：中心點 - 半徑 = 視覺的左上角 X
                            x = (viewModel.fallingIconX - iconSizePx / 2f).roundToInt(),
                            y = viewModel.fallingIconY.roundToInt()
                        )
                    }
                    // 2. 水平拖拽 (帶邊界檢查)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            // 計算新的 X 座標 (只使用 x 軸拖曳量)
                            val proposedX = viewModel.fallingIconX + dragAmount.x

                            // 計算邊界 (中心點 X 座標的最小/最大值)
                            val iconHalfWidth = iconSizePx / 2f
                            val minX = iconHalfWidth
                            val maxX = screenWidthPxFloat - iconHalfWidth

                            // 將 proposedX 限制在 [minX, maxX] 範圍內
                            val newX = proposedX.coerceIn(minX, maxX)

                            // 更新 X 座標 (Y 座標由 LaunchedEffect 控制，故垂直位置不動)
                            viewModel.updateIconXPosition(newX)
                        }
                    }
            )
        }
    }
}