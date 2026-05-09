package com.example.shale_namma_pride.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("users")
    
    private val _currentUser = MutableStateFlow(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    init {
        auth.addAuthStateListener {
            val user = it.currentUser
            _currentUser.value = user
            if (user != null) {
                Log.d("AuthRepository", "User logged in: ${user.email}")
                CoroutineScope(Dispatchers.IO).launch {
                    fetchUserRole(user.uid)
                }
            } else {
                Log.d("AuthRepository", "User logged out")
                _userRole.value = null
            }
        }
    }

    suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user!!
            fetchUserRole(user.uid)
            Result.success(user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign in failed", e)
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String, role: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            // Save role to database
            database.child(user.uid).child("role").setValue(role).await()
            Log.d("AuthRepository", "Saved role $role for user ${user.uid}")
            _userRole.value = role
            Result.success(user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Sign up failed", e)
            Result.failure(e)
        }
    }

    private suspend fun fetchUserRole(uid: String) {
        try {
            val snapshot = database.child(uid).child("role").get().await()
            val role = snapshot.getValue(String::class.java)
            Log.d("AuthRepository", "Fetched role: $role for uid: $uid")
            _userRole.value = role ?: "Parent" // Default to Parent if role not found
        } catch (e: Exception) {
            Log.e("AuthRepository", "Failed to fetch role", e)
            _userRole.value = "Parent" // Default to Parent on error
        }
    }

    fun signOut() {
        auth.signOut()
        _userRole.value = null
    }
}
