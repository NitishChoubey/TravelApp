package com.ebf.travelapp.ui.home

data class Booking (


        val placeId: String,  // Using placeId from NavGraph
        val placeName: String,
        val bookingDate: Long, // Timestamp in milliseconds
        val numberOfDays: Int,
        val travelerName: String,
        val travelerEmail: String

)