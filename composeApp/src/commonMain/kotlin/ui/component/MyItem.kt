package ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.items.Item
import ui.primary

@Composable
fun MyItem(modifier: Modifier = Modifier, item: Item) {
    Box (modifier = modifier .padding(top = 5.dp, bottom = 5.dp,
        start = 50.dp, ).fillMaxWidth(), contentAlignment = Alignment.BottomEnd){
        Card  (
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(topEnd = 10.dp,
                bottomStart = 10.dp ,  topStart = 10.dp),
            elevation = 1.dp,
            backgroundColor = Color(primary),
            border = BorderStroke(0.dp, Color.Transparent)
        ) {
            Text(
                item.value, modifier = Modifier.wrapContentSize()
                    .padding(vertical = 8.dp, horizontal = 10.dp),
                textAlign = TextAlign.End
            )
        }
    }
}