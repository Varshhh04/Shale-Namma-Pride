package com.example.shale_namma_pride.attendance

import com.example.shale_namma_pride.data.AppDao
import com.example.shale_namma_pride.data.Attendance
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepository @Inject constructor(
    private val appDao: AppDao
) {
    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("attendance")

    fun getLocalAttendance(schoolId: Long): Flow<List<Attendance>> {
        return appDao.getAttendanceForSchool(schoolId)
    }

    suspend fun saveAttendance(attendance: Attendance): Result<Unit> {
        return try {
            // Save locally for offline support
            appDao.insertAttendance(attendance)
            
            // Sync with Firebase for public transparency
            val dateKey = attendance.date.toString()
            firebaseDatabase.child(attendance.schoolId.toString()).child(dateKey).setValue(attendance).await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            // If firebase fails, we still have it locally. 
            // In a production app, we would implement a more robust sync mechanism.
            Result.success(Unit) 
        }
    }
}
