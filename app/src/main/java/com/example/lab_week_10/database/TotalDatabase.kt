package com.example.lab_week_10.database

import androidx.room.Database
import androidx.room.RoomDatabase

// Room database yang memakai entity Total
@Database(
    entities = [Total::class],
    version = 1,
    exportSchema = false
)
abstract class TotalDatabase : RoomDatabase() {

    // Dao yang dipakai untuk akses tabel "total"
    abstract fun totalDao(): TotalDao

    // Bisa tambahin Dao lain di sini kalau ada entity lain
}
