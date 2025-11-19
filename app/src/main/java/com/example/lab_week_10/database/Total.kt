package com.example.lab_week_10.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total")
// Setiap properti jadi kolom di tabel
data class Total(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    // Kolom berisi object yang punya value + date
    @Embedded
    val total: TotalObject = TotalObject()
)

// Object yang di-embed ke dalam kolom total
data class TotalObject(
    @ColumnInfo(name = "value") val value: Int = 0,
    @ColumnInfo(name = "date") val date: String = ""
)
