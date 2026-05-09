package com.example.shale_namma_pride.gallery

data class GalleryItem(
    val imageUrl: String,
    val category: GalleryCategory,
    val description: String
)

enum class GalleryCategory {
    LABS, LIBRARY, TOILETS
}
