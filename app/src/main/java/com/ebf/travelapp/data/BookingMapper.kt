package com.ebf.travelapp.data

import com.ebf.travelapp.ui.home.Booking

fun BookingEntity.toBooking(): Booking {
    return Booking(
        placeId = placeId,
        placeName = placeName,
        bookingDate = bookingDate,
        numberOfDays = numberOfDays,
        travelerName = travelerName,
        travelerEmail = travelerEmail
    )
}

fun Booking.toEntity(): BookingEntity {
    return BookingEntity(
        placeId = placeId,
        placeName = placeName,
        bookingDate = bookingDate,
        numberOfDays = numberOfDays,
        travelerName = travelerName,
        travelerEmail = travelerEmail
    )
}

