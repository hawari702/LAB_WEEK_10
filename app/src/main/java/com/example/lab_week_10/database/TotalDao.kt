package com.example.lab_week_10.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// Deklarasi Dao
@Dao
interface TotalDao {

    // Insert row baru, kalau id sudah ada → replace
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(total: Total)

    // Update row
    @Update
    fun update(total: Total)

    // Delete row
    @Delete
    fun delete(total: Total)

    // Query untuk ambil row berdasarkan id
    @Query("SELECT * FROM total WHERE id = :id")
    fun getTotal(id: Long): List<Total>
}
