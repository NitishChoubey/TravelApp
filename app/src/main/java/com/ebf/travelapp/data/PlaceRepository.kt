package com.ebf.travelapp.data

import com.ebf.travelapp.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor() {
    private val allPlaces = listOf(
        Place("1", "New Delhi", "India", R.drawable.lalqila, "Historic city.", 170.0),
        Place("2", "Mumbai", "India", R.drawable.gatewayofindia, "A fantastic place", 200.0),
        Place("3", "Kolkata", "India", R.drawable.victoriamemorial, "A cultural city.", 170.0),
        Place("4", "Chennai", "India", R.drawable.chennaihistoric, "A beautiful city.", 170.0),
        Place("4", "Ayodhya", "India", R.drawable.rammandir, "City of Lord Rama", 190.0),
        Place("6", "New York", "USA", R.drawable.landscape05, "A bustling city.", 220.0),
        Place("7", "Tokyo", "Japan", R.drawable.landscape06, "A vibrant city.", 190.0),
        Place("8", "Sydney", "Australia", R.drawable.landscape01, "A scenic city.", 210.0),
        Place("9", "Rome", "Italy", R.drawable.landscape02, "An ancient city.", 150.0),
        Place("10", "Borobudur", "Indonésie", R.drawable.landscape01, "A historic Buddhist temple.", 100.0),
        Place("11", "Monte Civetta", "Alpes", R.drawable.landscape02, "A beautiful mountain.", 150.0),
        Place("12", "Quseer Suez", "France", R.drawable.landscape06, "A coastal gem.", 200.0),
        Place("13", "Paris", "France", R.drawable.landscape03, "A beautiful city.", 180.0),
        Place("14", "London", "UK", R.drawable.landscape04, "A historic city.", 250.0),

    )

    fun getPlaces(query: String): List<Place> {
        return if (query.isEmpty()) {
            allPlaces
        } else {
            allPlaces.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }
        }
    }
}

