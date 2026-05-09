package com.example.shale_namma_pride.meals

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val repository: MealRepository
) : ViewModel() {

    private val _todayMeal = MutableStateFlow<Meal?>(null)
    val todayMeal: StateFlow<Meal?> = _todayMeal

    private val _uploadStatus = MutableStateFlow<Result<Unit>?>(null)
    val uploadStatus: StateFlow<Result<Unit>?> = _uploadStatus

    var capturedImageUri by mutableStateOf<android.net.Uri?>(null)

    init {
        loadTodayMeal()
    }

    fun loadTodayMeal() {
        viewModelScope.launch {
            _todayMeal.value = repository.getTodayMeal()
        }
    }

    fun uploadMeal(menuEn: String, menuKn: String, imageUrl: String) {
        viewModelScope.launch {
            val result = repository.uploadMeal(menuEn, menuKn, imageUrl)
            _uploadStatus.value = result
            if (result.isSuccess) {
                loadTodayMeal()
            }
        }
    }

    fun clearUploadStatus() {
        _uploadStatus.value = null
    }
}
