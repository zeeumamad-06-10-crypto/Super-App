package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.shop

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale

@Composable
fun ProductItem(product: Product ,navController: NavHostController) {
    Card(Modifier.fillMaxWidth().padding(8.dp)) {
        Column(Modifier.padding(12.dp)) {
            if (product.images.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.images[0])
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(product.title)
            Text("$${product.price}")
            Text(product.description, maxLines = 3)
        }
    }
}

