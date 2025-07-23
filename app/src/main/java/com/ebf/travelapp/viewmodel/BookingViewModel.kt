package com.ebf.travelapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebf.travelapp.data.BookingDao
import com.ebf.travelapp.data.toBooking
import com.ebf.travelapp.data.toEntity
import com.ebf.travelapp.ui.home.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingDao: BookingDao
) : ViewModel() {

    val bookings: Flow<List<Booking>> = bookingDao.getAllBookings()
        .map { entities -> entities.map { it.toBooking() } }

    fun saveBooking(booking: Booking) {
        viewModelScope.launch {
            bookingDao.insert(booking.toEntity())
        }
    }
}