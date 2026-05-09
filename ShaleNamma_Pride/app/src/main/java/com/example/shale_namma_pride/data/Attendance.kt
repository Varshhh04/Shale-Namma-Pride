package com.example.shale_namma_pride.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance")
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val schoolId: Long,
    val date: Long,
    val maleCount: Int,
    val femaleCount: Int,
    val staffPresent: Int
)
