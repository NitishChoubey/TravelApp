package com.ebf.travelapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val placeId: String,
    val placeName: String,
    val bookingDate: Long,
    val numberOfDays: Int,
    val travelerName: String,
    val travelerEmail: String
)