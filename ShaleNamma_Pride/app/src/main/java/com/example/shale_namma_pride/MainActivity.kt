package com.example.shale_namma_pride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.shale_namma_pride.data.LanguagePreferenceManager
import com.example.shale_namma_pride.attendance.AttendanceScreen
import com.example.shale_namma_pride.attendance.AttendanceViewModel
import com.example.shale_namma_pride.camera.CameraScreen
import com.example.shale_namma_pride.auth.AuthViewModel
import com.example.shale_namma_pride.auth.LoginScreen
import com.example.shale_namma_pride.feedback.FeedbackAdminScreen
import com.example.shale_namma_pride.feedback.FeedbackScreen
import com.example.shale_namma_pride.feedback.FeedbackViewModel
import com.example.shale_namma_pride.gallery.FacilityTourScreen
import com.example.shale_namma_pride.meals.MealFeedScreen
import com.example.shale_namma_pride.meals.MealUploadScreen
import com.example.shale_namma_pride.meals.MealViewModel
import com.example.shale_namma_pride.projects.ProjectsScreen
import com.example.shale_namma_pride.projects.ProjectViewModel
import com.example.shale_namma_pride.ui.*
import com.example.shale_namma_pride.ui.screens.SchoolsScreen
import com.example.shale_namma_pride.ui.theme.ShaleNamma_PrideTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var languagePreferenceManager: LanguagePreferenceManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val languageCode by languagePreferenceManager.languageFlow.collectAsState(initial = "en")
            val appLanguage = if (languageCode == "kn") AppLanguage.KN else AppLanguage.EN
            val scope = rememberCoroutineScope()
            var showSplash by remember { mutableStateOf(true) }

            ProvideStrings(language = appLanguage) {
                ShaleNamma_PrideTheme {
                    if (showSplash) {
                        SplashScreen(onSplashFinished = { showSplash = false })
                    } else {
                        val authViewModel: AuthViewModel = hiltViewModel()
                        val currentUser by authViewModel.currentUser.collectAsState()
                        val userRole by authViewModel.userRole.collectAsState()

                        if (currentUser == null) {
                            LoginScreen(authViewModel, onLoginSuccess = { })
                        } else if (userRole == null) {
                            // Loading state while role is being fetched
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        } else {
                            // User is logged in and role is known
                            val backStack = rememberNavBackStack(Screen.Schools as NavKey)
                            val currentScreen = backStack.last() as Screen
                            val strings = AppText.strings
                            val mealViewModel: MealViewModel = hiltViewModel()

                            NavigationSuiteScaffold(
                                navigationSuiteItems = {
                                    item(
                                        selected = currentScreen is Screen.Schools,
                                        onClick = { switchScreen(backStack, Screen.Schools) },
                                        icon = { Icon(Icons.Default.Business, contentDescription = null) },
                                        label = { Text(strings.schools) }
                                    )
                                    item(
                                        selected = currentScreen is Screen.Attendance,
                                        onClick = { switchScreen(backStack, Screen.Attendance) },
                                        icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                                        label = { Text(strings.attendance) }
                                    )
                                    item(
                                        selected = currentScreen is Screen.Projects,
                                        onClick = { switchScreen(backStack, Screen.Projects) },
                                        icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = null) },
                                        label = { Text(strings.projects) }
                                    )
                                    item(
                                        selected = currentScreen is Screen.Meals,
                                        onClick = { switchScreen(backStack, Screen.Meals) },
                                        icon = { Icon(Icons.Default.Restaurant, contentDescription = null) },
                                        label = { Text(strings.meals) }
                                    )
                                    item(
                                        selected = currentScreen is Screen.Gallery,
                                        onClick = { switchScreen(backStack, Screen.Gallery) },
                                        icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = null) },
                                        label = { Text(strings.gallery) }
                                    )
                                    item(
                                        selected = currentScreen is Screen.Feedback,
                                        onClick = { switchScreen(backStack, Screen.Feedback) },
                                        icon = { Icon(Icons.Default.Feedback, contentDescription = null) },
                                        label = { Text(strings.feedback) }
                                    )
                                }
                            ) {
                                Scaffold(
                                    topBar = {
                                        CenterAlignedTopAppBar(
                                            title = { Text(strings.appName) },
                                            actions = {
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        val nextLang = if (appLanguage == AppLanguage.EN) "kn" else "en"
                                                        languagePreferenceManager.saveLanguage(nextLang)
                                                    }
                                                }) {
                                                    Icon(Icons.Default.Language, contentDescription = "Toggle Language")
                                                }
                                                
                                                if (userRole == "Teacher") {
                                                    IconButton(onClick = {
                                                        if (currentScreen is Screen.Meals || currentScreen is Screen.MealsAdmin) {
                                                            switchScreen(backStack, Screen.MealsAdmin)
                                                        } else if (currentScreen is Screen.Feedback || currentScreen is Screen.FeedbackAdmin) {
                                                            switchScreen(backStack, Screen.FeedbackAdmin)
                                                        } else {
                                                            switchScreen(backStack, Screen.MealsAdmin)
                                                        }
                                                    }) {
                                                        Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin")
                                                    }
                                                }
                                                
                                                IconButton(onClick = { authViewModel.logout() }) {
                                                    Icon(Icons.Default.Logout, contentDescription = "Logout")
                                                }
                                            }
                                        )
                                    }
                                ) { innerPadding ->
                                    NavDisplay(
                                        backStack = backStack,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(innerPadding),
                                        entryProvider = entryProvider {
                                            addEntryProvider(Screen.Schools) { SchoolsScreen() }
                                            addEntryProvider(Screen.Attendance) {
                                                val viewModel: AttendanceViewModel = hiltViewModel()
                                                AttendanceScreen(viewModel, userRole)
                                            }
                                            addEntryProvider(Screen.Projects) {
                                                val viewModel: ProjectViewModel = hiltViewModel()
                                                ProjectsScreen(viewModel, userRole, onNavigateToCamera = { backStack.add(Screen.Camera) })
                                            }
                                            addEntryProvider(Screen.Camera) {
                                                CameraScreen(onImageCaptured = { uri -> 
                                                    mealViewModel.capturedImageUri = uri
                                                    backStack.removeAt(backStack.size - 1) 
                                                })
                                            }
                                            addEntryProvider(Screen.Meals) {
                                                MealFeedScreen(mealViewModel)
                                            }
                                            addEntryProvider(Screen.MealsAdmin) {
                                                MealUploadScreen(mealViewModel, onNavigateToCamera = { backStack.add(Screen.Camera) })
                                            }
                                            addEntryProvider(Screen.Gallery) { FacilityTourScreen() }
                                            addEntryProvider(Screen.Feedback) {
                                                val viewModel: FeedbackViewModel = hiltViewModel()
                                                FeedbackScreen(viewModel)
                                            }
                                            addEntryProvider(Screen.FeedbackAdmin) {
                                                val viewModel: FeedbackViewModel = hiltViewModel()
                                                FeedbackAdminScreen(viewModel)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun switchScreen(backStack: MutableList<NavKey>, screen: Screen) {
        if (backStack.lastOrNull() != screen) {
            backStack.clear()
            backStack.add(screen)
        }
    }
}
