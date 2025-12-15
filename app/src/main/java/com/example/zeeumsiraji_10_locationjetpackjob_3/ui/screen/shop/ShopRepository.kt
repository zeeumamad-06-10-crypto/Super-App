import com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.shop.Product
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
// ✅ Import CIO engine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ProductRepository {

    private val client = HttpClient(CIO) {   // ✅ Use CIO engine
        install(ContentNegotiation) {
            gson()
        }
    }

    private val baseUrl = "https://api.escuelajs.co/api/v1/"

    suspend fun fetchProducts(): List<Product> = withContext(Dispatchers.IO) {
        try {
            client.get("${baseUrl}products").body()
        } catch (e: IOException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    fun close() {
        client.close()
    }
}
