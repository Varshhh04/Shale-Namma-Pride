package com.example.shale_namma_pride.meals

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shale_namma_pride.ui.AppLanguage
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.LocalStrings
import com.example.shale_namma_pride.ui.KannadaStrings
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight

@Composable
fun MealFeedScreen(viewModel: MealViewModel) {
    val todayMeal by viewModel.todayMeal.collectAsState()
    val strings = AppText.strings
    val currentLang = LocalStrings.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = strings.todayMeal,
            style = MaterialTheme.typography.headlineMedium,
            color = VibrantPrimaryLight
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        if (todayMeal != null) {
            val menu = if (currentLang == KannadaStrings) todayMeal!!.menuKn else todayMeal!!.menuEn
            MealCard(meal = todayMeal!!, menuDisplay = menu)
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = strings.noMeal)
            }
        }
    }
}

@Composable
fun MealCard(meal: Meal, menuDisplay: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.extraLarge, // Child-friendly large shapes
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = "Meal Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = meal.date,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = menuDisplay,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun MealUploadScreen(viewModel: MealViewModel, onNavigateToCamera: () -> Unit = {}) {
    var menuEn by remember { mutableStateOf("") }
    var menuKn by remember { mutableStateOf("") }
    val uploadStatus by viewModel.uploadStatus.collectAsState()
    val strings = AppText.strings

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uploadStatus) {
        uploadStatus?.let {
            if (it.isSuccess) {
                snackbarHostState.showSnackbar("Meal uploaded successfully!")
                menuEn = ""
                menuKn = ""
                viewModel.capturedImageUri = null
            } else {
                snackbarHostState.showSnackbar(it.exceptionOrNull()?.message ?: "Upload failed")
            }
            viewModel.clearUploadStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = strings.uploadMeal,
                style = MaterialTheme.typography.headlineMedium,
                color = VibrantPrimaryLight
            )

            OutlinedTextField(
                value = menuEn,
                onValueChange = { menuEn = it },
                label = { Text(strings.menuEnglish) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = menuKn,
                onValueChange = { menuKn = it },
                label = { Text(strings.menuKannada) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.capturedImageUri?.toString() ?: "",
                onValueChange = { },
                label = { Text(strings.imageUrl) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = onNavigateToCamera) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = "Take Photo")
                    }
                }
            )

            Button(
                onClick = { viewModel.uploadMeal(menuEn, menuKn, viewModel.capturedImageUri?.toString() ?: "") },
                modifier = Modifier.fillMaxWidth(),
                enabled = menuEn.isNotBlank() && menuKn.isNotBlank() && viewModel.capturedImageUri != null,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(strings.uploadMeal)
            }
            
            Text(
                text = strings.oneUpdatePerDay,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
