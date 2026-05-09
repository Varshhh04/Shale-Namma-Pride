package com.example.shale_namma_pride.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // School queries
    @Query("SELECT * FROM schools")
    fun getAllSchools(): Flow<List<School>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchool(school: School)

    // Project queries
    @Query("SELECT * FROM projects WHERE schoolId = :schoolId")
    fun getProjectsForSchool(schoolId: Long): Flow<List<Project>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: Project): Long

    // Attendance queries
    @Query("SELECT * FROM attendance WHERE schoolId = :schoolId ORDER BY date DESC")
    fun getAttendanceForSchool(schoolId: Long): Flow<List<Attendance>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance): Long

    // Feedback queries
    @Query("SELECT * FROM feedback ORDER BY timestamp DESC")
    fun getAllFeedback(): Flow<List<Feedback>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: Feedback): Long
}
