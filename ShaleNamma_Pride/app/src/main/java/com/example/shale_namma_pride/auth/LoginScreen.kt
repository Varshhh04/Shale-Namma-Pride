package com.example.shale_namma_pride.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight

@Composable
fun LoginScreen(viewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    var isRegister by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Parent") }
    var passwordVisible by remember { mutableStateOf(false) }
    
    val strings = AppText.strings
    val error = viewModel.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Identity
        Surface(
            color = VibrantPrimaryLight,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("ಶ", color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = strings.appName,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = VibrantPrimaryLight
        )
        Text(
            text = if (isRegister) "Create Account" else "Welcome Back",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        
        Spacer(modifier = Modifier.height(40.dp))

        // Input Fields
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            shape = MaterialTheme.shapes.large,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.large,
            singleLine = true
        )

        if (isRegister) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Select Role", modifier = Modifier.align(Alignment.Start), style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = role == "Parent",
                    onClick = { role = "Parent" },
                    label = { Text("Parent") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = role == "Teacher",
                    onClick = { role = "Teacher" },
                    label = { Text("Teacher") },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (isRegister) {
                    viewModel.register(email, password, role, onLoginSuccess)
                } else {
                    viewModel.login(email, password, onLoginSuccess)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(containerColor = VibrantPrimaryLight)
        ) {
            Text(if (isRegister) "Sign Up" else "Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        TextButton(
            onClick = { 
                isRegister = !isRegister
                viewModel.clearError()
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(if (isRegister) "Already have an account? Login" else "New here? Create an account")
        }
    }
}
