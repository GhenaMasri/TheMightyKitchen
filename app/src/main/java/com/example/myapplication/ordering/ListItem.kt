package com.example.myapplication.ordering

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme


@Composable
fun ItemWithQuantity(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onMinusClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    val (quantity, setQuantity) = remember { mutableStateOf(0) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val image: Painter = painterResource(id = R.drawable.food)
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(80.dp)
        )

        // Title and description column
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .weight(1f)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }

        // Quantity box with buttons
        Box(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (quantity > 0) {
                            setQuantity(quantity - 1)
                            onMinusClicked()
                        }
                    },
                ) {
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Minus")
                }
                Text(
                    text = quantity.toString(),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(
                    onClick = {
                        setQuantity(quantity + 1)
                        onAddClicked()
                    }
                ) {
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Add")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ItemPreview(){
    MyApplicationTheme {
        ItemWithQuantity(modifier = Modifier ,title = "Name", description = "Description", onAddClicked = {}, onMinusClicked = {})
    }
}

