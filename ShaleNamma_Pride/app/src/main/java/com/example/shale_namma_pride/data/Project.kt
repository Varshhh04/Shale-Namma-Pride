package com.example.shale_namma_pride.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val schoolId: Long,
    val name: String,
    val description: String,
    val status: String, // e.g., PLANNED, IN_PROGRESS, COMPLETED
    val startDate: Long,
    val endDate: Long? = null
)
