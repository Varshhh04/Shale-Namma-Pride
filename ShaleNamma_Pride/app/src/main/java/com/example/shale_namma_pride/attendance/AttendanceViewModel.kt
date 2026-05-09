package com.example.shale_namma_pride.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shale_namma_pride.data.Attendance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val repository: AttendanceRepository
) : ViewModel() {

    private val _schoolId = MutableStateFlow<Long>(1L) // Default for demo

    val attendanceHistory: StateFlow<List<Attendance>> = repository.getLocalAttendance(1L)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveAttendance(male: Int, female: Int, staff: Int) {
        viewModelScope.launch {
            val attendance = Attendance(
                schoolId = 1L,
                date = System.currentTimeMillis(),
                maleCount = male,
                femaleCount = female,
                staffPresent = staff
            )
            repository.saveAttendance(attendance)
        }
    }
}
