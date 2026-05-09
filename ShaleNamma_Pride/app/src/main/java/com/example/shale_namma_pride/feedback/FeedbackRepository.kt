package com.example.shale_namma_pride.feedback

import com.example.shale_namma_pride.data.AppDao
import com.example.shale_namma_pride.data.Feedback
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackRepository @Inject constructor(
    private val appDao: AppDao
) {
    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("feedback")

    fun getFeedback(): Flow<List<Feedback>> {
        return appDao.getAllFeedback()
    }

    suspend fun submitFeedback(feedback: Feedback) {
        val feedbackToUpload = if (feedback.isAnonymous) {
            feedback.copy(userId = null, userName = null)
        } else {
            feedback
        }
        
        val id = appDao.insertFeedback(feedbackToUpload)
        val feedbackWithId = feedbackToUpload.copy(id = id)
        // Sync with Firebase
        firebaseDatabase.child(id.toString()).setValue(feedbackWithId).await()
    }
}
