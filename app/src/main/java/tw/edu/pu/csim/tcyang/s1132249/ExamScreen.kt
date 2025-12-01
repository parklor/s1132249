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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import tw.edu.pu.csim.tcyang.s1132249.R

@Composable
fun ExamScreen(viewModel: ExamViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
}