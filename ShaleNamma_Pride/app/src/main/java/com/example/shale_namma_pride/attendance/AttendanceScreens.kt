package com.example.shale_namma_pride.attendance

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight
import com.example.shale_namma_pride.ui.theme.VibrantSecondaryLight
import com.example.shale_namma_pride.ui.theme.VibrantTertiaryLight
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AttendanceScreen(viewModel: AttendanceViewModel, userRole: String?) {
    val history by viewModel.attendanceHistory.collectAsState()
    val strings = AppText.strings

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = strings.attendance,
            style = MaterialTheme.typography.headlineMedium,
            color = VibrantPrimaryLight
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        // Only show Recording Card for Teachers
        if (userRole == "Teacher") {
            AttendanceRecordingCard(onSave = viewModel::saveAttendance)
            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            text = strings.trends,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(history) { entry ->
                AttendanceTrendCard(entry)
            }
        }
    }
}

@Composable
fun AttendanceRecordingCard(onSave: (Int, Int, Int) -> Unit) {
    var male by remember { mutableStateOf("") }
    var female by remember { mutableStateOf("") }
    var staff by remember { mutableStateOf("") }
    val strings = AppText.strings

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = male,
                    onValueChange = { male = it },
                    label = { Text(strings.male) },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = female,
                    onValueChange = { female = it },
                    label = { Text(strings.female) },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = staff,
                onValueChange = { staff = it },
                label = { Text(strings.staff) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { 
                    onSave(male.toIntOrNull() ?: 0, female.toIntOrNull() ?: 0, staff.toIntOrNull() ?: 0)
                    male = ""; female = ""; staff = ""
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(strings.save)
            }
        }
    }
}

@Composable
fun AttendanceTrendCard(entry: com.example.shale_namma_pride.data.Attendance) {
    val total = entry.maleCount + entry.femaleCount
    val maxExpected = 100f // Demo constant
    val progress = (total / maxExpected).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(targetValue = progress)
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
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(entry.date)),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "${strings.totalAttendance}: $total",
                    fontWeight = FontWeight.Bold,
                    color = VibrantPrimaryLight
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(MaterialTheme.shapes.small),
                color = VibrantSecondaryLight,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TrendChip(label = strings.male, value = entry.maleCount, color = VibrantTertiaryLight)
                TrendChip(label = strings.female, value = entry.femaleCount, color = VibrantSecondaryLight)
                TrendChip(label = strings.staff, value = entry.staffPresent, color = VibrantPrimaryLight)
            }
        }
    }
}

@Composable
fun TrendChip(label: String, value: Int, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).background(color, MaterialTheme.shapes.small))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "$label: $value", style = MaterialTheme.typography.bodySmall)
    }
}
