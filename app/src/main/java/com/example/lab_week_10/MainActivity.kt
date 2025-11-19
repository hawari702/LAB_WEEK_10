package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {

    // DB Room, dibuat lazy supaya baru dibuat ketika dipakai
    private val db: TotalDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java,
            "total-database"
        )
            .allowMainThreadQueries() // hanya untuk latihan, di real app sebaiknya pakai background thread
            .build()
    }

    // ViewModel
    private lateinit var viewModel: TotalViewModel

    companion object {
        // ID tunggal untuk row Total
        const val ID: Long = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[TotalViewModel::class.java]

        // Ambil nilai awal dari database dan set ke ViewModel
        initializeValueFromDatabase()

        // Observe LiveData → setiap total berubah, update UI
        viewModel.total.observe(this) { total ->
            updateText(total)
        }

        // Tombol increment → update ViewModel (nanti Room di-update saat onPause)
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // Baca nilai awal dari database
    private fun initializeValueFromDatabase() {
        val rows = db.totalDao().getTotal(ID)

        if (rows.isEmpty()) {
            // Kalau belum ada row, buat baru dengan total = 0
            db.totalDao().insert(Total(id = ID, total = 0))
            viewModel.setTotal(0)
        } else {
            // Kalau sudah ada, pakai nilai dari DB
            val current = rows.first().total
            viewModel.setTotal(current)
        }
    }

    // Simpan nilai terbaru ke DB setiap Activity di-pause
    override fun onPause() {
        super.onPause()
        val current = viewModel.total.value ?: 0
        db.totalDao().update(Total(ID, current))
    }
}
