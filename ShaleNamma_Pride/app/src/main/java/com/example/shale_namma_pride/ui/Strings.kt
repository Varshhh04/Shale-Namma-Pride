package com.example.shale_namma_pride.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

enum class AppLanguage {
    EN, KN
}

data class AppStrings(
    val appName: String,
    val schools: String,
    val attendance: String,
    val projects: String,
    val meals: String,
    val gallery: String,
    val language: String,
    val todayMeal: String,
    val noMeal: String,
    val menuDesc: String,
    val uploadMeal: String,
    val facilityTour: String,
    val male: String,
    val female: String,
    val staff: String,
    val save: String,
    val totalAttendance: String,
    val trends: String,
    val budget: String,
    val status: String,
    val milestone: String,
    val captureEvidence: String,
    val geoTagging: String,
    val feedback: String,
    val submitFeedback: String,
    val anonymousToggle: String,
    val feedbackHint: String,
    val feedbackSuccess: String,
    val adminView: String,
    val feedbackFrom: String,
    val anonymous: String,
    val menuEnglish: String,
    val menuKannada: String,
    val imageUrl: String,
    val oneUpdatePerDay: String
)

val EnglishStrings = AppStrings(
    appName = "Shale Namma Pride",
    schools = "Schools",
    attendance = "Attendance",
    projects = "Projects",
    meals = "Meals",
    gallery = "Gallery",
    language = "Language",
    todayMeal = "Today's Mid-Day Meal",
    noMeal = "No meal updated yet for today.",
    menuDesc = "Menu Description",
    uploadMeal = "Upload Meal",
    facilityTour = "Facility Tour",
    male = "Male",
    female = "Female",
    staff = "Staff",
    save = "Save",
    totalAttendance = "Total Attendance",
    trends = "Trends",
    budget = "Budget",
    status = "Status",
    milestone = "Milestone",
    captureEvidence = "Capture Evidence",
    geoTagging = "Geo-tagging Enabled",
    feedback = "Feedback",
    submitFeedback = "Submit Feedback",
    anonymousToggle = "Send Anonymously",
    feedbackHint = "Share your suggestions or concerns...",
    feedbackSuccess = "Feedback sent successfully!",
    adminView = "Admin Dashboard",
    feedbackFrom = "From",
    anonymous = "Anonymous",
    menuEnglish = "Menu (English)",
    menuKannada = "Menu (Kannada)",
    imageUrl = "Image URL",
    oneUpdatePerDay = "Note: Only one update allowed per day."
)

val KannadaStrings = AppStrings(
    appName = "ಶಾಲೆ ನಮ್ಮ ಹೆಮ್ಮೆ",
    schools = "ಶಾಲೆಗಳು",
    attendance = "ಹಾಜರಾತಿ",
    projects = "ಯೋಜನೆಗಳು",
    meals = "ಊಟ",
    gallery = "ಗ್ಯಾಲರಿ",
    language = "ಭಾಷೆ",
    todayMeal = "ಇಂದಿನ ಮಧ್ಯಾಹ್ನದ ಊಟ",
    noMeal = "ಇಂದು ಇನ್ನೂ ಯಾವುದೇ ಊಟವನ್ನು ಅಪ್‌ಡೇಟ್ ಮಾಡಿಲ್ಲ.",
    menuDesc = "ಮೆನು ವಿವರಣೆ",
    uploadMeal = "ಊಟವನ್ನು ಅಪ್‌ಲೋಡ್ ಮಾಡಿ",
    facilityTour = "ಸೌಲಭ್ಯಗಳ ಪ್ರವಾಸ",
    male = "ಗಂಡು",
    female = "ಹೆಣ್ಣು",
    staff = "ಸಿಬ್ಬಂದಿ",
    save = "ಉಳಿಸಿ",
    totalAttendance = "ಒಟ್ಟು ಹಾಜರಾತಿ",
    trends = "ಟ್ರೆಂಡ್ಸ್",
    budget = "ಬಜೆಟ್",
    status = "ಸ್ಥಿತಿ",
    milestone = "ಮೈಲಿಗಲ್ಲು",
    captureEvidence = "ಸಾಕ್ಷ್ಯವನ್ನು ಸೆರೆಹಿಡಿಯಿರಿ",
    geoTagging = "ಜಿಯೋ-ಟ್ಯಾಗಿಂಗ್ ಸಕ್ರಿಯಗೊಳಿಸಲಾಗಿದೆ",
    feedback = "ಪ್ರತಿಕ್ರಿಯೆ",
    submitFeedback = "ಪ್ರತಿಕ್ರಿಯೆಯನ್ನು ಸಲ್ಲಿಸಿ",
    anonymousToggle = "ಅನಾಮಧೇಯವಾಗಿ ಕಳುಹಿಸಿ",
    feedbackHint = "ನಿಮ್ಮ ಸಲಹೆಗಳು ಅಥವಾ ಕಾಳಜಿಗಳನ್ನು ಹಂಚಿಕೊಳ್ಳಿ...",
    feedbackSuccess = "ಪ್ರತಿಕ್ರಿಯೆಯನ್ನು ಯಶಸ್ವಿಯಾಗಿ ಕಳುಹಿಸಲಾಗಿದೆ!",
    adminView = "ನಿರ್ವಾಹಕ ಡ್ಯಾಶ್‌ಬೋರ್ಡ್",
    feedbackFrom = "ಇವರಿಂದ",
    anonymous = "ಅನಾಮಧೇಯ",
    menuEnglish = "ಮೆನು (ಇಂಗ್ಲಿಷ್)",
    menuKannada = "ಮೆನು (ಕನ್ನಡ)",
    imageUrl = "ಚಿತ್ರದ ಲಿಂಕ್",
    oneUpdatePerDay = "ಸೂಚನೆ: ದಿನಕ್ಕೆ ಒಂದು ಬಾರಿ ಮಾತ್ರ ಅಪ್‌ಡೇಟ್ ಮಾಡಲು ಅವಕಾಶವಿದೆ."
)

val LocalStrings = staticCompositionLocalOf { EnglishStrings }

@Composable
fun ProvideStrings(language: AppLanguage, content: @Composable () -> Unit) {
    val strings = if (language == AppLanguage.KN) KannadaStrings else EnglishStrings
    CompositionLocalProvider(LocalStrings provides strings, content = content)
}

object AppText {
    val strings: AppStrings
        @Composable
        @ReadOnlyComposable
        get() = LocalStrings.current
}
