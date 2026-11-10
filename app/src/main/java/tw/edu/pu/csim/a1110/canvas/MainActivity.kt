package tw.edu.pu.csim.a1110.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import tw.edu.pu.csim.a1110.canvas.ui.theme.CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CanvasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {
    var CanvasWidth by remember { mutableStateOf(value = 0f) }
    var CamvaHeight by remember { mutableStateOf(value = 0f) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // 繪製洋紅色的圓, 半徑 100, 圓心 (300,800)
            drawCircle(
                Color.Magenta,
                radius = 100f,
                center = Offset(x = 300f, y = 800f)
            )

            // 繪製藍色空心矩形, 左上角 (300,200), 寬度與高度 200
            drawRect(
                Color.Blue,
                topLeft = Offset(x = 300f, y = 200f),
                Size(width = 200f, height = 200f),
                style = Stroke(width = 10f) // 使用 style 控制外框樣式
            )

            CanvasWidth = size.width
            CamvaHeight = size.height

            // 繪製紅色的線，從 (200,800) 畫到右下角，線條粗細 10
            drawLine(
                Color.Red,
                start = Offset(x = 200f, y = 800f),
                end = Offset(x = size.width, y = size.height),
                strokeWidth = 10f
            )
        }

        Text("畫布寬度 $CanvasWidth, 高度 $CamvaHeight")
    }
}