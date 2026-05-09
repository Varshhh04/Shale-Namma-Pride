package com.example.shale_namma_pride.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shale_namma_pride.ui.AppText
import com.example.shale_namma_pride.ui.theme.VibrantPrimaryLight

@Composable
fun FacilityTourScreen() {
    val categories = GalleryCategory.entries
    var selectedCategory by remember { mutableStateOf(GalleryCategory.LABS) }
    val strings = AppText.strings
    
    // Sample data
    val items = remember(selectedCategory) {
        when(selectedCategory) {
            GalleryCategory.LABS -> listOf(
                GalleryItem("https://images.unsplash.com/photo-1562774053-701939374585?w=500", GalleryCategory.LABS, "Modern Science Lab"),
                GalleryItem("https://images.unsplash.com/photo-1518152006812-edab29b069ac?w=500", GalleryCategory.LABS, "Computer Science Center")
            )
            GalleryCategory.LIBRARY -> listOf(
                GalleryItem("https://images.unsplash.com/photo-1521587760476-6c12a4b040da?w=500", GalleryCategory.LIBRARY, "Main Reading Hall"),
                GalleryItem("https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?w=500", GalleryCategory.LIBRARY, "Kids Section")
            )
            GalleryCategory.TOILETS -> listOf(
                GalleryItem("https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=500", GalleryCategory.TOILETS, "Clean Handwash Area"),
                GalleryItem("https://images.unsplash.com/photo-1527515637462-cff94eecc1ac?w=500", GalleryCategory.TOILETS, "Sanitary Facilities")
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = strings.facilityTour,
            style = MaterialTheme.typography.headlineMedium,
            color = VibrantPrimaryLight,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = categories.indexOf(selectedCategory),
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = VibrantPrimaryLight
        ) {
            categories.forEach { category ->
                Tab(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    text = { Text(category.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val pagerState = rememberPagerState(pageCount = { items.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp
        ) { page ->
            GalleryCard(item = items[page])
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "${pagerState.currentPage + 1} / ${items.size}",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun GalleryCard(item: GalleryItem) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.description,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
            ) {
                Text(
                    text = item.description,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
