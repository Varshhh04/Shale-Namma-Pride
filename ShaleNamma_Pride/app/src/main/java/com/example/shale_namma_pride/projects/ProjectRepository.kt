package com.example.shale_namma_pride.projects

import com.example.shale_namma_pride.data.AppDao
import com.example.shale_namma_pride.data.Project
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepository @Inject constructor(
    private val appDao: AppDao
) {
    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("projects")

    fun getProjects(schoolId: Long): Flow<List<Project>> {
        return appDao.getProjectsForSchool(schoolId)
    }

    suspend fun saveProject(project: Project) {
        val id = appDao.insertProject(project)
        val projectWithId = project.copy(id = id)
        // Sync with Firebase
        firebaseDatabase.child(project.schoolId.toString()).child(id.toString()).setValue(projectWithId).await()
    }
}
