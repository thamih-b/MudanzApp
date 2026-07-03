

package com.example.appmudanza.ui.theme.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStars(rating: Float) {
    Row(modifier = Modifier.padding(4.dp)) {
        for (i in 1..5) {
            if (i.toFloat() <= rating) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107))
            } else {
                Icon(Icons.Outlined.Star, contentDescription = null, tint = Color(0xFFFFC107))
            }
        }
    }
}