package tw.edu.pu.csim.tcyang.s1132249

// ExamScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.tcyang.s1132249.R

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {

    val density = LocalDensity.current
    val imageSizeDp: Dp = with(density) { 300.toDp() }
    val screenHeightDp: Dp = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow) // 黄色背景
            .padding(16.dp)
    ) {

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

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "瑪利亞基金會服務大考驗")
            Text(text = "作者:資管二B 羅婉薰")

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "螢幕大小: ${viewModel.screenWidthPx.toFloat()} * ${viewModel.screenHeightPx.toFloat()}")

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "成績: ${viewModel.score} 分")
        }
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = "Baby",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.TopStart)
                .offset(y = screenHeightDp * 0.5f- imageSizeDp)
        )

        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = "Child",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.TopEnd)
                .offset(y = screenHeightDp * 0.5f- imageSizeDp)
        )

        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = "Adult",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.BottomStart)
        )

        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = "Citizen",
            modifier = Modifier
                .size(imageSizeDp)
                .align(Alignment.BottomEnd)
        )
    }
}