package ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import book_gemini.composeapp.generated.resources.Res
import book_gemini.composeapp.generated.resources.compose_multiplatform
import book_gemini.composeapp.generated.resources.ic_bot
import data.items.Item
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AIItem(item: Item ) {

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
                Text(
                    item.value, modifier = Modifier.wrapContentSize()
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }
        }
    }
}