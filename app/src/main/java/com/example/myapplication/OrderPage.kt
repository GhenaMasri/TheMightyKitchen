package com.example.myapplication


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ordering.ItemWithQuantity
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun OrderPage(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Content including LazyColumn
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(20) {
                    ItemWithQuantity(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp), // Increase height here
                        title = "Name",
                        description = "Description",
                        onAddClicked = {},
                        onMinusClicked = {}
                    )
                }
            }
        }
        // Static checkout button
        Button(
            onClick = { /* Handle checkout button click */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Checkout", fontSize = 16.sp)
        }
    }
}



@Preview
@Composable
private fun OrderPagePreview() {
    MyApplicationTheme {
        OrderPage()
    }
}