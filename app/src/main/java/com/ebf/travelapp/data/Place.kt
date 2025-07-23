package com.ebf.travelapp.data

data class Place(
    val id: String, // Changed to String to match placeId
    val name: String,
    val location: String,
    val imageResId: Int, // Drawable resource ID
    val description: String,
    val pricePerDay: Double
)