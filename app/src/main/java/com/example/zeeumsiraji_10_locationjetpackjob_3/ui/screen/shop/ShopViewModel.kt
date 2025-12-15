package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.shop
import ProductRepository
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

sealed class ProductUiState {
    object Loading : ProductUiState()
    data class Success(val products: List<Product>) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
}

class ProductViewModel : ViewModel() {

    private val repo = ProductRepository()

    var uiState by mutableStateOf<ProductUiState>(ProductUiState.Loading)
        private set

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            uiState = ProductUiState.Loading
            try {
                val products = repo.fetchProducts()
                uiState = ProductUiState.Success(products)
            } catch (e: IOException) {
                uiState = ProductUiState.Error("No internet connection. Tap Retry.")
            } catch (e: Exception) {
                uiState = ProductUiState.Error("Failed to load products. Tap Retry.")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repo.close()
    }
}
