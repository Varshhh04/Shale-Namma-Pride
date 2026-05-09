package com.example.shale_namma_pride.meals

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import android.net.Uri
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor() {
    private val database = FirebaseDatabase.getInstance().getReference("daily_meals")
    private val storage = FirebaseStorage.getInstance().getReference("meal_images")

    suspend fun getTodayMeal(): Meal? {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val snapshot = database.child(date).get().await()
        return snapshot.getValue(Meal::class.java)
    }

    suspend fun uploadMeal(menuEn: String, menuKn: String, imageUriString: String): Result<Unit> {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        // Strict Constraint: Only one update per day
        val existing = database.child(date).get().await()
        if (existing.exists()) {
            return Result.failure(Exception("Meal already updated for today!"))
        }

        return try {
            val imageUri = Uri.parse(imageUriString)
            val imageRef = storage.child("$date.jpg")
            imageRef.putFile(imageUri).await()
            val downloadUrl = imageRef.downloadUrl.await().toString()

            val meal = Meal(
                date = date,
                menuEn = menuEn,
                menuKn = menuKn,
                imageUrl = downloadUrl,
                timestamp = System.currentTimeMillis()
            )

            database.child(date).setValue(meal).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
