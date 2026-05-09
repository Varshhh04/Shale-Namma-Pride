package com.example.shale_namma_pride.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "feedback")
data class Feedback(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val schoolId: Long = 1, // Default for demo
    val userId: String? = null,
    val userName: String? = null,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isAnonymous: Boolean = false
)
