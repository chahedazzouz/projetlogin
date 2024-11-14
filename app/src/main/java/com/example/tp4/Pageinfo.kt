package com.example.tp4

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp4.ui.theme.Tp4Theme

@Composable
fun ProductDetailScreen(
    name: String,
    price: String,
    color: String,
    quantity: String,
    resultOnFace: String,
    description: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Product: $name", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Price: $price €", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Color: $color", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Quantity: $quantity", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result on Face: $resultOnFace", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Description: $description", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    Tp4Theme {
        ProductDetailScreen(
            name = "Cosmetic Product 1",
            price = "10.8",
            color = "Red",
            quantity = "50ml",
            resultOnFace = "Smooth and glowing skin",
            description = "This is a high-quality cosmetic product that improves skin texture."
        )
    }
}
