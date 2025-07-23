package com.ebf.travelapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookingEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao
}

