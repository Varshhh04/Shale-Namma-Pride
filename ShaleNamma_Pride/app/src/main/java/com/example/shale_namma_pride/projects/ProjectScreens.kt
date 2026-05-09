package com.example.shale_namma_pride.projects

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shale_namma_pride.data.Project
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight

@OptIn(androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProjectsScreen(viewModel: ProjectViewModel, userRole: String?, onNavigateToCamera: () -> Unit = {}) {
    val projects by viewModel.projects.collectAsState()
    val navigator = rememberListDetailPaneScaffoldNavigator<Project>()
    val strings = AppText.strings
    val scope = rememberCoroutineScope()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ProjectListPane(
                projects = projects,
                userRole = userRole,
                onProjectClick = { 
                    scope.launch {
                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, it) 
                    }
                },
                onAddProject = { viewModel.addProject("New Initiative", "Description of progress") }
            )
        },
        detailPane = {
            val project = navigator.currentDestination?.contentKey
            if (project != null) {
                ProjectDetailPane(project = project, userRole = userRole, onCapture = onNavigateToCamera)
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(strings.projects)
                }
            }
        }
    )
}

@Composable
fun ProjectListPane(
    projects: List<Project>,
    userRole: String?,
    onProjectClick: (Project) -> Unit,
    onAddProject: () -> Unit
) {
    val strings = AppText.strings
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.projects,
                style = MaterialTheme.typography.headlineMedium,
                color = VibrantPrimaryLight
            )
            if (userRole == "Teacher") {
                Button(onClick = onAddProject) {
                    Text("+")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(projects) { project ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onProjectClick(project) },
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = project.name, fontWeight = FontWeight.Bold)
                        Text(text = project.status, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun ProjectDetailPane(project: Project, userRole: String?, onCapture: () -> Unit) {
    val strings = AppText.strings
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(
            text = project.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = project.description)
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(text = "${strings.status}: ${project.status}", fontWeight = FontWeight.Medium)
        Text(text = "${strings.budget}: ₹50,000 (Allocated)", color = VibrantPrimaryLight) // Mock budget
        
        if (userRole == "Teacher") {
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = onCapture,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(strings.captureEvidence)
            }
        }
    }
}
