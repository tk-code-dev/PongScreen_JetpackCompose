package sample.tk.pongscreen_jetpackcompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PongGame()
        }
    }
}

@Composable
fun PongGame() {
    var ballPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var ballVelocity by remember { mutableStateOf(Offset(10f, 10f)) }
    val ballRadius = 50f.dp
    val screenWidth = 720f.dp
    val screenHeight = 1280f.dp

    LaunchedEffect(Unit) {
        while (true) {
            ballPosition += ballVelocity
            if (ballPosition.x - ballRadius.value < 0 || ballPosition.x + ballRadius.value > screenWidth.value) {
                ballVelocity = Offset(-ballVelocity.x, ballVelocity.y)
            }
            if (ballPosition.y - ballRadius.value < 0 || ballPosition.y + ballRadius.value > screenHeight.value) {
                ballVelocity = Offset(ballVelocity.x, -ballVelocity.y)
            }
            delay(16)
        }
    }

    val ballColor = animateFloatAsState(
        targetValue = if (ballVelocity.x > 0) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    ).value

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .offset(
                x = (ballPosition.x - ballRadius.value).dp,
                y = (ballPosition.y - ballRadius.value).dp
            )
    ) {
        drawCircle(
            color = Color(
                red = ballColor,
                green = 0f,
                blue = 1 - ballColor,
                alpha = 1f
            ),
            radius = ballRadius.value,
            style = Stroke(5f)
        )
    }
}