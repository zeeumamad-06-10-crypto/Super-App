package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MyShopScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {
    val uiState by remember { productViewModel::uiState }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is ProductUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is ProductUiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { productViewModel.loadProducts() }) {
                        Text("Retry")
                    }
                }
            }
            is ProductUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.products) { product ->
                        ProductItem(product, navController)
                    }
                }
            }
        }
    }
}


