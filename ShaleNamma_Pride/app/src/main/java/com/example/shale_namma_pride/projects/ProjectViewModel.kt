package com.example.shale_namma_pride.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shale_namma_pride.data.Project
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectViewModel @Inject constructor(
    private val repository: ProjectRepository
) : ViewModel() {

    private val _schoolId = MutableStateFlow(1L)

    val projects: StateFlow<List<Project>> = repository.getProjects(1L)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addProject(name: String, description: String) {
        viewModelScope.launch {
            val project = Project(
                schoolId = 1L,
                name = name,
                description = description,
                status = "PLANNED",
                startDate = System.currentTimeMillis()
            )
            repository.saveProject(project)
        }
    }
}
