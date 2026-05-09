package com.example.shale_namma_pride.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shale_namma_pride.data.Feedback
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight

@Composable
fun FeedbackScreen(viewModel: FeedbackViewModel) {
    var content by remember { mutableStateOf("") }
    var isAnonymous by remember { mutableStateOf(false) }
    val strings = AppText.strings

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = strings.feedback,
            style = MaterialTheme.typography.headlineMedium,
            color = VibrantPrimaryLight
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text(strings.feedbackHint) },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            shape = MaterialTheme.shapes.large
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(strings.anonymousToggle)
            Switch(
                checked = isAnonymous,
                onCheckedChange = { isAnonymous = it }
            )
        }

        Button(
            onClick = {
                if (content.isNotBlank()) {
                    viewModel.sendFeedback(content, isAnonymous)
                    content = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(strings.submitFeedback)
        }
    }
}

@Composable
fun FeedbackAdminScreen(viewModel: FeedbackViewModel) {
    val feedbacks by viewModel.allFeedback.collectAsState()
    val strings = AppText.strings

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = strings.adminView,
            style = MaterialTheme.typography.headlineMedium,
            color = VibrantPrimaryLight
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(feedbacks) { feedback ->
                FeedbackItem(feedback)
            }
        }
    }
}

@Composable
fun FeedbackItem(feedback: Feedback) {
    val strings = AppText.strings
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (feedback.isAnonymous) strings.anonymous else (feedback.userName ?: strings.anonymous),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = java.text.DateFormat.getDateInstance().format(java.util.Date(feedback.timestamp)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedback.content)
        }
    }
}
