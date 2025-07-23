package com.ebf.travelapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ebf.travelapp.ui.components.Menu
import com.ebf.travelapp.viewmodel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MyBookingsScreen(
    navBack: () -> Unit,
    viewModel: BookingViewModel,
    modifier: Modifier
) {
    val bookings by viewModel.bookings.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Menu(pressBack = navBack, title = "My Bookings")
        }
    ) { padding ->
        if (bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No bookings yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                items(bookings) { booking ->
                    BookingItem(booking)
                }
            }
        }
    }
}




@Composable
fun BookingItem(booking: Booking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Place: ${booking.placeName}", style = MaterialTheme.typography.h6)
            Text(
                text = "Date: ${
                    SimpleDateFormat("dd/MM/yyyy").format(Date(booking.bookingDate))
                }"
            )
            Text(text = "Duration: ${booking.numberOfDays} days")
            Text(text = "Traveler: ${booking.travelerName}")
            Text(text = "Email: ${booking.travelerEmail}")
        }
    }
}