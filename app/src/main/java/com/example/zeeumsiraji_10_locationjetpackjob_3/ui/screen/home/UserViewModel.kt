package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.home



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zeeumsiraji_10_locationjetpackjob_3.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        // 🔥 Firestore listener
        db.collection("users")
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.documents?.map { doc ->
                    User(
                        userId = doc.id,
                        name = doc.getString("name") ?: "",
                        email = doc.getString("email") ?: "",
                        location = doc.getString("location") ?: "",
                        latitude = doc.getDouble("latitude"),
                        longitude = doc.getDouble("longitude")
                    )
                } ?: emptyList()
                _users.value = list
            }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateUser(user: User, name: String, email: String, location: String, latitude: Double?, longitude: Double?, onResult: (Boolean) -> Unit) {
        val updateMap = mutableMapOf<String, Any>(
            "name" to name,
            "email" to email,
            "location" to location
        )
        latitude?.let { updateMap["latitude"] = it }
        longitude?.let { updateMap["longitude"] = it }

        db.collection("users").document(user.userId)
            .update(updateMap)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun deleteUser(user: User, onResult: (Boolean) -> Unit) {
        db.collection("users").document(user.userId)
            .delete()
            .addOnSuccessListener {
                auth.currentUser?.delete()
                onResult(true)
            }
            .addOnFailureListener { onResult(false) }
    }
}
