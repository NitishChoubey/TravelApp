package com.ebf.travelapp.data

import androidx.room.Insert
import androidx.room.Query
import androidx.room.vo.Dao

import java.util.concurrent.Flow

@androidx.room.Dao
interface BookingDao {
    @Insert
    suspend fun insert(booking: BookingEntity)

    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("DELETE FROM bookings")
    suspend fun deleteAll()
}

