package tw.edu.pu.csim.tcyang.s1132249

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExamViewModel : ViewModel() {
    var screenWidthPx by mutableStateOf(0)
        private set
    var screenHeightPx by mutableStateOf(0)
        private set

    var score by mutableStateOf(0)
        private set

    fun updateScreenDimensions(width: Int, height: Int) {
        screenWidthPx = width
        screenHeightPx = height
    }
}