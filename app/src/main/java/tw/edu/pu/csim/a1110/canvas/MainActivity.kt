package tw.edu.pu.csim.a1110.canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
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

    // 小小兵的 X、Y 座標
    var minionX by remember { mutableStateOf(100f) }
    var minionY by remember { mutableStateOf(400f) }

    // 控制是否正在移動的狀態
    var isMoving by remember { mutableStateOf(false) }

    // 載入圖片資源 (小小兵)
    val minionsImage: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.minions)

    // 當 isMoving 為 true 時，每隔 1 秒讓小小兵移動
    LaunchedEffect(isMoving) {
        if (isMoving) {
            while (true) {
                delay(1000L) // 每 1 秒
                minionX += 80f // X 軸每次移動 80 像素
                minionY += 80f // Y 軸每次移動 80 像素

                // 如果移動超過畫布邊界，重置回初始位置
                if (CanvasWidth > 0 && (minionX > CanvasWidth || minionY > CamvaHeight)) {
                    minionX = 50f
                    minionY = 200f
                }
            }
        }
    }

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
                style = Stroke(width = 10f)
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

            // 繪製移動的小小兵
            translate(left = minionX, top = minionY) {
                drawImage(minionsImage)
            }
        }

        // 上方顯示文字資訊
        Text(
            text = "畫布寬度 $CanvasWidth, 高度 $CamvaHeight\n小小兵座標: (${minionX.toInt()}, ${minionY.toInt()})",
            modifier = Modifier.padding(16.dp)
        )

        // 下方放置控制按鈕（開始/繼續、結束、重新開始）
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { isMoving = true },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(if (minionX == 100f && minionY == 400f) "開始" else "繼續")
            }

            Button(
                onClick = { isMoving = false },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("結束")
            }

            Button(
                onClick = {
                    isMoving = false
                    minionX = 100f
                    minionY = 400f
                },
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text("重新開始")
            }
        }
    }
}