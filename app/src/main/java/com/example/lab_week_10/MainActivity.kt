package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.database.TotalObject
import com.example.lab_week_10.viewmodels.TotalViewModel
import java.util.Date

class MainActivity : AppCompatActivity() {

    // DB Room
    private val db: TotalDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java,
            "total-database"
        )
            .fallbackToDestructiveMigration()  // ← TAMBAH INI
            .allowMainThreadQueries()          // cuma untuk latihan
            .build()
    }


    private lateinit var viewModel: TotalViewModel

    companion object {
        const val ID: Long = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[TotalViewModel::class.java]

        // Inisialisasi nilai dari database
        initializeValueFromDatabase()

        // Observe total → update UI
        viewModel.total.observe(this) { total ->
            updateText(total)
        }

        // Tombol increment
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            viewModel.incrementTotal()
        }
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    // Ambil nilai awal dari DB, kalau belum ada → insert
    private fun initializeValueFromDatabase() {
        val rows = db.totalDao().getTotal(ID)

        if (rows.isEmpty()) {
            val initial = Total(
                id = ID,
                total = TotalObject(
                    value = 0,
                    date = Date().toString()
                )
            )
            db.totalDao().insert(initial)
            viewModel.setTotal(0)
        } else {
            val current = rows.first().total.value
            viewModel.setTotal(current)
        }
    }

    override fun onStart() {
        super.onStart()
        // Tampilkan tanggal terakhir update sebagai Toast
        val rows = db.totalDao().getTotal(ID)
        if (rows.isNotEmpty()) {
            val lastDate = rows.first().total.date
            if (lastDate.isNotEmpty()) {
                Toast.makeText(this, lastDate, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Simpan nilai + tanggal terbaru ke DB
        val current = viewModel.total.value ?: 0
        val updated = Total(
            id = ID,
            total = TotalObject(
                value = current,
                date = Date().toString()
            )
        )
        db.totalDao().update(updated)
    }
}
