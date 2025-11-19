package com.example.lab_week_10.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TotalViewModel : ViewModel() {

    // Declare the LiveData object
    private val _total = MutableLiveData<Int>()
    val total: LiveData<Int> = _total

    // Initialize the LiveData object
    init {
        _total.value = 0   // nilai awal 0
    }

    // Increment the total value
    fun incrementTotal() {
        val current = _total.value ?: 0
        _total.value = current + 1
        // kalau nanti dipanggil dari background thread, baru pakai postValue
        // _total.postValue(current + 1)
    }
}
