package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AuthState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    // Sign In
    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        _authState.value = AuthState(loading = true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    _authState.value = AuthState(loading = false, success = true)
                    onResult(true, null)
                } else {
                    _authState.value = AuthState(loading = false, error = task.exception?.message)
                    onResult(false, task.exception?.message)
                }
            }
    }

    // Sign Up
    fun signUp(name: String, email: String, password: String, location: String, onResult: (Boolean, String?) -> Unit) {
        _authState.value = AuthState(loading = true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    val userMap = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "location" to location
                    )
                    firestore.collection("users").document(uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            _authState.value = AuthState(loading = false, success = true)
                            onResult(true, null)
                        }
                        .addOnFailureListener { e ->
                            _authState.value = AuthState(loading = false, error = e.message)
                            onResult(false, e.message)
                        }
                } else {
                    _authState.value = AuthState(loading = false, error = task.exception?.message)
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}
