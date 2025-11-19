package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TotalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[TotalViewModel::class.java]

        // Tampilkan nilai awal
        updateText(viewModel.total)

        // Setup button
        prepareViewModel()
    }

    private fun updateText(total: Int) {
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel() {
        findViewById<Button>(R.id.button_increment).setOnClickListener {
            val newTotal = viewModel.incrementTotal()
            updateText(newTotal)
        }
    }
}
