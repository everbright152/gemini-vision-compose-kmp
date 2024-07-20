package ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import book_gemini.composeapp.generated.resources.Res
import book_gemini.composeapp.generated.resources.compose_multiplatform
import book_gemini.composeapp.generated.resources.ic_bot
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.gray
import ui.primary
import ui.primaryDark

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoadingItem( ) {
    Box (modifier = Modifier .padding(top = 5.dp, bottom = 5.dp,
        end = 50.dp,  )){
        Row(modifier = Modifier) {
            Image(
                painter = painterResource(Res.drawable.ic_bot),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .background(shape = RoundedCornerShape(50), color = Color.Transparent)
            )
            Card  (
                modifier = Modifier.wrapContentSize(),
                shape = RoundedCornerShape(topEnd = 10.dp,
                    bottomEnd = 10.dp , bottomStart = 10.dp),
                elevation = 1.dp
            ) {

                val dots =  (1..4).map { remember { Animatable(0.2f) } }

                dots.forEachIndexed { index, animatable ->
                    LaunchedEffect(animatable) {
                        delay(index * 200L)
                        animatable.animateTo(
                            targetValue = 1f, animationSpec = infiniteRepeatable(
                                animation = tween(600, easing = FastOutLinearInEasing),
                                repeatMode = RepeatMode.Reverse,
                            )
                        )
                    }
                }

                val dys = dots.map { it.value }

                Row(
                    modifier = Modifier.wrapContentSize()
                        .padding(vertical = 15.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    dys.forEachIndexed { index, dy ->

                        Box(
                            Modifier
                                .size(20.dp)
                                .scale(dy)
                                .alpha(dy)
                                .background(color = Color(primaryDark), shape = CircleShape)
                        )

                    }
                }
            }
        }
    }
}