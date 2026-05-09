package com.example.shale_namma_pride.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shale_namma_pride.data.Feedback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val repository: FeedbackRepository
) : ViewModel() {

    val allFeedback: StateFlow<List<Feedback>> = repository.getFeedback()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun sendFeedback(content: String, isAnonymous: Boolean) {
        viewModelScope.launch {
            val feedback = Feedback(
                content = content,
                isAnonymous = isAnonymous,
                userName = if (isAnonymous) null else "Parent User" // Placeholder for demo
            )
            repository.submitFeedback(feedback)
        }
    }
}
