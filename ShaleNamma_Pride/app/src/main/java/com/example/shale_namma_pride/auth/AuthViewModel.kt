package com.example.shale_namma_pride.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    val currentUser = repository.currentUser
    val userRole = repository.userRole

    var error by mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = repository.signIn(email, password)
            if (result.isSuccess) {
                onSuccess()
            } else {
                error = result.exceptionOrNull()?.message
            }
        }
    }

    fun register(email: String, password: String, role: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = repository.signUp(email, password, role)
            if (result.isSuccess) {
                onSuccess()
            } else {
                error = result.exceptionOrNull()?.message
            }
        }
    }

    fun clearError() {
        error = null
    }

    fun logout() {
        repository.signOut()
    }
}
